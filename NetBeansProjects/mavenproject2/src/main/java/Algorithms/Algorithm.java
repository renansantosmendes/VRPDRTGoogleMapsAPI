/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

/**
 *
 * @author renansantos
 */
public interface Algorithm {
    Object builGreedySoltution();
    Object builRandomSoltution();
    Object reBuildSolution();
    void evaluateSolution();
    Object localSeach();
    void readInstance();
}
