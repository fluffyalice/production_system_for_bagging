package assignmenttwo.production;

import assignmenttwo.logger.LoggerHandler;
import assignmenttwo.pmatch.MString;
import assignmenttwo.pmatch.MStringVector;
import assignmenttwo.pmatch.MatchDetails;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * ProdSys.java -
 * class for production system engine
 * 2013 Version Phil Green
 * <p>
 * 2020 Heidi Christensen (heidi.christensen@sheffield.ac.uk)
 */
public class ProductionSystem {
    private static final Logger LOGGER = LoggerHandler.getLogger();

    public List<String> runProductionSystem(List<String> shortTermMemory) {
        LOGGER.log(Level.INFO, "Running the Production System:");
        boolean productionFired = true;

        while (productionFired) {
            productionFired = false; // will be reset to true when a production is fired

            MStringVector shortTermMemoryVector = new MStringVector();
            shortTermMemory.forEach(shortTermMemoryVector::add);

            for(Production production : Production.PRODUCTIONS_LIST) {
                if (productionFired) break; // iterate over the productions until one fires or run out
//                LOGGER.log(Level.INFO, "-------------------- NEW PRODUCTION --------------------\n" +
//                                "Short Term Memory = {0}\nTrying to fire: {1}...",
//                        new Object[]{shortTermMemory, production.getName()});

                MStringVector antecedents = new MStringVector(production.getAntecedents()); // its antecedents as an MStringVector
                List<MatchDetails> partialMatches = new ArrayList<>();
                // initiallly 1 partial with all the antecedents and an empty context
                partialMatches.add(new MatchDetails(new HashMap<>(), "", antecedents));

                // try to develop partial matches by matching next antecedents
                // continue until partial matches run out or a production fires
                while (!partialMatches.isEmpty() && !productionFired) {
//                    LOGGER.log(Level.INFO, "Start of while; partial matches are: {0}",
//                            partialMatches.stream()
//                                    .map(MatchDetails::getRemainingAntecedents)
//                                    .collect(Collectors.toList()));

                    MatchDetails partialMatch = partialMatches.get(0);
                    partialMatches.remove(0);
                    Map<String, Object> context = partialMatch.getContext();
                    MStringVector remainingAntecedents = partialMatch.getRemainingAntecedents();

//                    LOGGER.log(Level.INFO, "Developing a partial match with context {0} & remaining antecedents: {1}",
//                            new Object[]{context, remainingAntecedents});
                    // if no more antecedents and the production's predicate returns true in the context, fire the production
                    if (remainingAntecedents.getVector().isEmpty()) {
                        if (production.canRunInContext(context)) {
                            fireProduction(shortTermMemory, production, context);
                            productionFired = true;
                        }
                    } else { // there are more antecedents, find matches for the first one & create new partial matches
                        String nextAntecedent = (remainingAntecedents.getVector().get(0)).getBaseString(); // next antecedent to try
//                        LOGGER.log(Level.INFO, "Next antecedent => {0}", nextAntecedent);
                        // Updating remaining antecedents
                        List<MString> remainingAntecedentsVector = new ArrayList<>(remainingAntecedents.getVector());
                        remainingAntecedentsVector.remove(0);
                        remainingAntecedents.setVector(remainingAntecedentsVector);
//                        LOGGER.log(Level.INFO, "Trying to match next antecedent against ShortTermMemory...");
                        // match the next antecedent against stm
                        boolean creations = shortTermMemoryVector.matchAll(nextAntecedent, context);

                        if (creations) { // matches found, each one creates new partial
                            List<MatchDetails> matchDetailsList = shortTermMemoryVector.getMatchDetailsList();
//                            LOGGER.log(Level.INFO, "Success!\n{0} matches found.", matchDetailsList.size());
                            matchDetailsList.forEach(nextPartialMatch ->
                                    nextPartialMatch.setRemainingAntecedents(remainingAntecedents));
                            // in which this antecedent is now removed
                            // these partial matches have remaining antecedents

                            partialMatches.addAll(0, matchDetailsList); // put them at the front of the partial list

//                            LOGGER.log(Level.INFO, "Partial matches are: {0}",
//                                    partialMatches.stream()
//                                            .map(MatchDetails::getRemainingAntecedents)
//                                            .collect(Collectors.toList()));
                        } else {
//                            LOGGER.log(Level.INFO, "No matches found.");
                        }
                    }
                }
            }
        }
        LOGGER.log(Level.INFO, "RUN TERMINATED");
        return shortTermMemory; // return final stm
    }// end of runProductionSystem

    // fire a given production, given the context
    private void fireProduction(List<String> shortTermMemory, Production production, Map<String, Object> inContext) {
        Map<String, Object> context = production.modifyContext(inContext); // call the context modifier
        String productionName = production.getName();
        MStringVector additionsMatchString = new MStringVector(production.getAdditions());// make MStringVectors for the productions adds, dels & remarks
        MStringVector deletionsMatchString = new MStringVector(production.getDeletions());
        MStringVector remarksMatchString = new MStringVector(production.getRemarks());

        List<String> additions = additionsMatchString.mSubst(context); // use these to substitute for the context
        List<String> deletions = deletionsMatchString.mSubst(context);
        List<String> remarks = remarksMatchString.mSubst(context);
        LOGGER.log(Level.INFO, "Deletions: {0}\nFiring {1} in context {2}\n{1} remarks: {3}\n\n",
                new Object[]{deletions, productionName, context, remarks});

        shortTermMemory.removeAll(deletions);
        shortTermMemory.addAll(additions);
    }
}
