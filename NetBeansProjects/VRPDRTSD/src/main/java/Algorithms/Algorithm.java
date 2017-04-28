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

    default <Candidates, Candidate, ProblemSolution> ProblemSolution buildGreedySolution() {
        ProblemSolution solution = initializeSolution();
        List<Candidates> candidatesList = initializeCandidatesElementsSet();
        while (!candidatesList.isEmpty()) {
            Candidate candidate = findBestCandidate();
            addCandidateIntoSolution(candidate);
            candidatesList = actualizeCandidatesElementsSet();
        }
        return solution;
    }

    public <ProblemSolution> ProblemSolution initializeSolution();

    public <Candidates> Candidates initializeCandidatesElementsSet();

    public <Candidate> Candidate findBestCandidate();

    public <Candidate, ProblemSolution> void addCandidateIntoSolution(Candidate candidate);

    public <Candidates> Candidates actualizeCandidatesElementsSet();
}
