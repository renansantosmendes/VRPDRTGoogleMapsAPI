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
public interface Algorithm {
    
    void readInstance();
    
    
    default <ProblemSolution, ProblemData, Candidates> void buildGreedySolution(ProblemSolution solution, ProblemData... data){
        List<Candidates> candidates = InitializeCandidateElementsSet(data);
        int cursor = 0;
        while(!candidates.isEmpty()){
            System.out.println(candidates.get(cursor));
            candidates.remove(cursor);
            cursor++;
        }
    }
    
    public <Candidates, ProblemSolution, ProblemData> List<Candidates> InitializeCandidateElementsSet(ProblemData... data);
    
//    Object builRandomSoltution();
//    
//    Object reBuildSolution();
//    
//    void evaluateSolution();
//    
//    Object localSeach();
//    

  
    
}
