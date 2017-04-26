/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import java.util.List;

/**
 *
 * @author renansantos
 */
public interface Algorithm<ProblemSolution, ProblemData, Candidates, Candidate> {

    void readInstance();

    public <Candidates> Candidates initializeCandidatesElementsSet();

    public <Candidates> Candidates actualizeCandidatesElementsSet();

    public <Candidate> Candidate findBestCandidate();

    public <Candidate, ProblemSolution> void addCandidateIntoSolution(ProblemSolution solution, Candidate candidate);

    default <Candidates, Candidate, ProblemSolution> ProblemSolution buildGreedySolution() {
        ProblemSolution solution = null;
        List<Candidates> candidatesList = initializeCandidatesElementsSet();
        while (!candidatesList.isEmpty()) {
            Candidate candidate = findBestCandidate();
            addCandidateIntoSolution(solution, candidate);
            candidatesList = actualizeCandidatesElementsSet();
        }
        return solution;
    }

//    Object builRandomSoltution();
//    
//    Object reBuildSolution();
//    
//    void evaluateSolution();
//    
//    Object localSeach();   
}
