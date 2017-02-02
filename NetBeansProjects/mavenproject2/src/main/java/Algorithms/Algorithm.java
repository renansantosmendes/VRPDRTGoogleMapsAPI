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
public interface Algorithm <ProblemSolution, ProblemData, Candidates> {
    
    void readInstance();
    
    
    default <ProblemSolution, ProblemData, Candidates> void buildGreedySolution(ProblemSolution solution, ProblemData[] data){
        List<Candidates> candidates = InitializeCandidateElementsSet(data);
        int cursor = 0;
        while(!candidates.isEmpty()){
            System.out.println(candidates.get(cursor));
            candidates.remove(cursor);
            cursor++;
        }
    }
    
    public <Candidates, ProblemSolution, ProblemData> List<Candidates> InitializeCandidateElementsSet(ProblemData[] data);
    
    public <ProblemData> void testMethod();
    
    public <Candidates> Candidates initializeCandidatesElementsSet();
    
    public <Candidates> Candidates actualizeCandidatesElementsSet();
    
    default <Candidates, ProblemSolution> void defaultBuildGreedySolution(){
        ProblemSolution solution = null;
        List<Candidates> candidatesList = initializeCandidatesElementsSet();
        while(!candidatesList.isEmpty()){
            candidatesList = actualizeCandidatesElementsSet();
            System.out.println(candidatesList.size());
        }
    }
    
//    Object builRandomSoltution();
//    
//    Object reBuildSolution();
//    
//    void evaluateSolution();
//    
//    Object localSeach();
//    

  
    
}
