/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritmos;

import static Algoritmos.Algoritmos.FO;
import static Algoritmos.Algoritmos.FO1;
import static Algoritmos.Algoritmos.FO2;
import static Algoritmos.Algoritmos.FO3;
import static Algoritmos.Algoritmos.FO4;
import static Algoritmos.Algoritmos.FO5;
import static Algoritmos.Algoritmos.FO6;
import static Algoritmos.Algoritmos.FO7;
import static Algoritmos.Algoritmos.FO8;
import static Algoritmos.Algoritmos.FO9;
import static Algoritmos.Algoritmos.FOagregados;
import static Algoritmos.Algoritmos.FOp;
import static Algoritmos.Algoritmos.FuncaoDeAvaliacao;
import static Algoritmos.Algoritmos.ILS;
import static Algoritmos.Algoritmos.Perturbacao;
import static Algoritmos.Algoritmos.PerturbacaoSemente;
import static Algoritmos.Algoritmos.VND;
//import static Algoritmos.Algoritmos.GeraSolucaoAleatoria;
import static Algoritmos.Algoritmos.avaliaSolucao;
import static Algoritmos.Algoritmos.greedyConstructive;
import static Algoritmos.AlgoritmosMO.Inicializa;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import representacao.Request;
import representacao.Rota;
import representacao.Solucao;

/**
 *
 * @author Renan
 */
public class Funcoes {

    public static void loaderProblem(List<Request> P, Set<Integer> Pmais, Set<Integer> Pmenos,
            Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout,
            Set<Integer> V, Integer n, List<Integer> m) {

        /**
         * Iniciando dados do problema - LISTA DE SOLICITA��ES
         */
        P.clear();

        Request req1 = new Request(1, 5, 9, 0 * 60 + 20, 0 * 60 + 50);
        //System.out.println(req1);
        P.add(req1);

        // EXTRA
        P.add(new Request(1001, 5, 9, 0 * 60 + 20, 0 * 60 + 50));
        P.add(new Request(1002, 5, 9, 0 * 60 + 20, 0 * 60 + 50));
        P.add(new Request(1003, 5, 9, 0 * 60 + 20, 0 * 60 + 50));
        P.add(new Request(1004, 5, 9, 0 * 60 + 20, 0 * 60 + 50));
        P.add(new Request(1005, 5, 9, 0 * 60 + 20, 0 * 60 + 50));
        P.add(new Request(1006, 5, 9, 0 * 60 + 21, 0 * 60 + 50));
        P.add(new Request(1007, 5, 9, 0 * 60 + 22, 0 * 60 + 50));
        P.add(new Request(1008, 5, 9, 0 * 60 + 23, 0 * 60 + 50));
        P.add(new Request(1009, 5, 9, 0 * 60 + 24, 0 * 60 + 50));
        P.add(new Request(1010, 5, 9, 0 * 60 + 25, 0 * 60 + 50));

        P.add(new Request(1011, 2, 5, 0 * 60 + 16, 2 * 60 + 40));
        P.add(new Request(1012, 2, 5, 0 * 60 + 16, 2 * 60 + 40));
        P.add(new Request(1013, 2, 5, 0 * 60 + 16, 2 * 60 + 40));
        P.add(new Request(1014, 2, 5, 0 * 60 + 16, 2 * 60 + 40));
        P.add(new Request(1015, 2, 5, 0 * 60 + 16, 2 * 60 + 40));
        P.add(new Request(1016, 2, 5, 0 * 60 + 17, 2 * 60 + 40));
        P.add(new Request(1017, 2, 5, 0 * 60 + 18, 2 * 60 + 40));
        P.add(new Request(1018, 2, 5, 0 * 60 + 19, 2 * 60 + 40));
        P.add(new Request(1019, 2, 5, 0 * 60 + 20, 2 * 60 + 40));
        P.add(new Request(1020, 2, 5, 0 * 60 + 21, 2 * 60 + 40));

        P.add(new Request(1021, 2, 9, 0 * 60 + 30, 0 * 60 + 41));
        P.add(new Request(1022, 2, 9, 0 * 60 + 30, 0 * 60 + 41));
        P.add(new Request(1023, 2, 9, 0 * 60 + 30, 0 * 60 + 41));
        P.add(new Request(1024, 2, 9, 0 * 60 + 30, 0 * 60 + 41));
        P.add(new Request(1025, 2, 9, 0 * 60 + 30, 0 * 60 + 41));
        P.add(new Request(1026, 2, 9, 0 * 60 + 31, 0 * 60 + 41));
        P.add(new Request(1027, 2, 9, 0 * 60 + 32, 0 * 60 + 41));
        P.add(new Request(1028, 2, 9, 0 * 60 + 33, 0 * 60 + 41));
        P.add(new Request(1029, 2, 9, 0 * 60 + 34, 0 * 60 + 41));
        P.add(new Request(1030, 2, 9, 0 * 60 + 35, 0 * 60 + 41));

        P.add(new Request(1031, 1, 3, 0 * 60 + 2, 0 * 60 + 25));
        P.add(new Request(1032, 1, 3, 0 * 60 + 2, 0 * 60 + 25));
        P.add(new Request(1033, 1, 3, 0 * 60 + 2, 0 * 60 + 25));
        P.add(new Request(1034, 1, 3, 0 * 60 + 2, 0 * 60 + 25));
        P.add(new Request(1035, 1, 3, 0 * 60 + 2, 0 * 60 + 25));
        P.add(new Request(1036, 1, 3, 0 * 60 + 3, 0 * 60 + 25));
        P.add(new Request(1037, 1, 3, 0 * 60 + 4, 0 * 60 + 25));
        P.add(new Request(1038, 1, 3, 0 * 60 + 5, 0 * 60 + 25));
        P.add(new Request(1039, 1, 3, 0 * 60 + 6, 0 * 60 + 25));
        P.add(new Request(1040, 1, 3, 0 * 60 + 7, 0 * 60 + 25));

        P.add(new Request(1041, 1, 5, 0 * 60 + 2, 1 * 60 + 21));
        P.add(new Request(1042, 1, 5, 0 * 60 + 2, 1 * 60 + 21));
        P.add(new Request(1043, 1, 5, 0 * 60 + 2, 1 * 60 + 21));
        P.add(new Request(1044, 1, 5, 0 * 60 + 2, 1 * 60 + 21));
        P.add(new Request(1045, 1, 5, 0 * 60 + 2, 1 * 60 + 21));
        P.add(new Request(1046, 1, 5, 0 * 60 + 3, 1 * 60 + 21));
        P.add(new Request(1047, 1, 5, 0 * 60 + 4, 1 * 60 + 21));
        P.add(new Request(1048, 1, 5, 0 * 60 + 5, 1 * 60 + 21));
        P.add(new Request(1049, 1, 5, 0 * 60 + 6, 1 * 60 + 21));
        P.add(new Request(1050, 1, 5, 0 * 60 + 7, 1 * 60 + 21));

        Request req2 = new Request(2, 5, 9, 0 * 60 + 20, 0 * 60 + 50);
        //System.out.println(req2);
        P.add(req2);

        Request req3 = new Request(3, 1, 3, 0 * 60 + 2, 0 * 60 + 25);
        //System.out.println(req3);
        P.add(req3);

        Request req4 = new Request(4, 1, 3, 0 * 60 + 7, 0 * 60 + 25);
        //System.out.println(req4);
        P.add(req4);

        Request req5 = new Request(5, 1, 8, 0 * 60 + 25, 0 * 60 + 45);
        //System.out.println(req5);
        P.add(req5);

        Request req6 = new Request(6, 8, 1, 0 * 60 + 38, 0 * 60 + 58);
        //System.out.println(req6);
        P.add(req6);

        /**
         * INICIO - SOLICITA��ES GERADAS ALEATORIAMENTE *
         */
        Request req7 = new Request(7, 1, 5, 2 * 60 + 27, 2 * 60 + 49);
        //System.out.println(req7);
        P.add(req7);

        Request req8 = new Request(8, 2, 5, 0 * 60 + 16, 2 * 60 + 40);
        //System.out.println(req8);
        P.add(req8);

        Request req9 = new Request(9, 3, 10, 2 * 60 + 21, 2 * 60 + 50);
        //System.out.println(req9);
        P.add(req9);

        Request req10 = new Request(10, 1, 4, 0 * 60 + 10, 0 * 60 + 32);
        //System.out.println(req10);
        P.add(req10);

        Request req11 = new Request(11, 10, 1, 2 * 60 + 3, 2 * 60 + 53);
        //System.out.println(req11);
        P.add(req11);

        Request req12 = new Request(12, 5, 10, 0 * 60 + 8, 0 * 60 + 54);
        //System.out.println(req12);
        P.add(req12);

        Request req13 = new Request(13, 9, 11, 1 * 60 + 14, 1 * 60 + 52);
        //System.out.println(req13);
        P.add(req13);

        Request req14 = new Request(14, 6, 10, 2 * 60 + 27, 2 * 60 + 39);
        //System.out.println(req14);
        P.add(req14);

        Request req15 = new Request(15, 9, 2, 0 * 60 + 56, 1 * 60 + 14);
        //System.out.println(req15);
        P.add(req15);

        Request req16 = new Request(16, 10, 7, 1 * 60 + 0, 2 * 60 + 8);
        //System.out.println(req16);
        P.add(req16);

        Request req17 = new Request(17, 3, 9, 0 * 60 + 0, 1 * 60 + 19);
        //System.out.println(req17);
        P.add(req17);

        Request req18 = new Request(18, 11, 6, 1 * 60 + 4, 1 * 60 + 50);
        //System.out.println(req18);
        P.add(req18);

        Request req19 = new Request(19, 5, 7, 1 * 60 + 38, 1 * 60 + 57);
        //System.out.println(req19);
        P.add(req19);

        Request req20 = new Request(20, 1, 5, 0 * 60 + 2, 1 * 60 + 21);
        //System.out.println(req20);
        P.add(req20);

        Request req21 = new Request(21, 2, 5, 0 * 60 + 51, 2 * 60 + 56);
        //System.out.println(req21);
        P.add(req21);

        Request req22 = new Request(22, 5, 1, 1 * 60 + 44, 2 * 60 + 0);
        //System.out.println(req22);
        P.add(req22);

        Request req23 = new Request(23, 7, 2, 0 * 60 + 23, 0 * 60 + 43);
        //System.out.println(req23);
        P.add(req23);

        Request req24 = new Request(24, 6, 4, 1 * 60 + 44, 2 * 60 + 18);
        //System.out.println(req24);
        P.add(req24);

        Request req25 = new Request(25, 11, 7, 0 * 60 + 27, 2 * 60 + 8);
        //System.out.println(req25);
        P.add(req25);

        Request req26 = new Request(26, 7, 3, 2 * 60 + 5, 2 * 60 + 46);
        //System.out.println(req26);
        P.add(req26);

        Request req27 = new Request(27, 5, 1, 1 * 60 + 5, 1 * 60 + 26);
        //System.out.println(req27);
        P.add(req27);

        Request req28 = new Request(28, 6, 4, 2 * 60 + 6, 2 * 60 + 32);
        //System.out.println(req28);
        P.add(req28);

        Request req29 = new Request(29, 8, 1, 0 * 60 + 29, 0 * 60 + 53);
        //System.out.println(req29);
        P.add(req29);

        Request req30 = new Request(30, 2, 6, 2 * 60 + 8, 2 * 60 + 32);
        //System.out.println(req30);
        P.add(req30);

        Request req31 = new Request(31, 4, 3, 1 * 60 + 18, 1 * 60 + 42);
        //System.out.println(req31);
        P.add(req31);

        Request req32 = new Request(32, 9, 10, 1 * 60 + 3, 1 * 60 + 39);
        //System.out.println(req32);
        P.add(req32);

        Request req33 = new Request(33, 8, 3, 2 * 60 + 13, 2 * 60 + 31);
        //System.out.println(req33);
        P.add(req33);

        Request req34 = new Request(34, 10, 6, 1 * 60 + 46, 1 * 60 + 59);
        //System.out.println(req34);
        P.add(req34);

        Request req35 = new Request(35, 7, 10, 1 * 60 + 31, 1 * 60 + 42);
        //System.out.println(req35);
        P.add(req35);

        Request req36 = new Request(36, 6, 1, 1 * 60 + 1, 2 * 60 + 24);
        //System.out.println(req36);
        P.add(req36);

        Request req37 = new Request(37, 10, 6, 0 * 60 + 3, 2 * 60 + 31);
        //System.out.println(req37);
        P.add(req37);

        Request req38 = new Request(38, 9, 7, 1 * 60 + 31, 2 * 60 + 36);
        //System.out.println(req38);
        P.add(req38);

        Request req39 = new Request(39, 3, 6, 1 * 60 + 21, 2 * 60 + 16);
        //System.out.println(req39);
        P.add(req39);

        Request req40 = new Request(40, 6, 10, 1 * 60 + 8, 2 * 60 + 18);
        //System.out.println(req40);
        P.add(req40);

        Request req41 = new Request(41, 8, 1, 1 * 60 + 21, 2 * 60 + 21);
        //System.out.println(req41);
        P.add(req41);

        Request req42 = new Request(42, 5, 7, 1 * 60 + 7, 1 * 60 + 32);
        //System.out.println(req42);
        P.add(req42);

        Request req43 = new Request(43, 3, 4, 1 * 60 + 30, 2 * 60 + 29);
        //System.out.println(req43);
        P.add(req43);

        Request req44 = new Request(44, 8, 6, 0 * 60 + 52, 1 * 60 + 4);
        //System.out.println(req44);
        P.add(req44);

        Request req45 = new Request(45, 2, 9, 1 * 60 + 33, 2 * 60 + 18);
        //System.out.println(req45);
        P.add(req45);

        Request req46 = new Request(46, 2, 9, 0 * 60 + 30, 0 * 60 + 41);
        //System.out.println(req46);
        P.add(req46);

        Request req47 = new Request(47, 8, 1, 2 * 60 + 7, 2 * 60 + 58);
        //System.out.println(req47);
        P.add(req47);

        Request req48 = new Request(48, 5, 1, 0 * 60 + 33, 2 * 60 + 38);
        //System.out.println(req48);
        P.add(req48);

        Request req49 = new Request(49, 6, 8, 0 * 60 + 52, 1 * 60 + 29);
        //System.out.println(req49);
        P.add(req49);

        Request req50 = new Request(50, 2, 8, 1 * 60 + 27, 1 * 60 + 52);
        //System.out.println(req50);
        P.add(req50);

        Request req51 = new Request(51, 7, 5, 0 * 60 + 3, 0 * 60 + 56);
        //System.out.println(req51);
        P.add(req51);

        Request req52 = new Request(52, 9, 4, 2 * 60 + 4, 2 * 60 + 18);
        //System.out.println(req52);
        P.add(req52);

        Request req53 = new Request(53, 10, 8, 0 * 60 + 19, 2 * 60 + 32);
        //System.out.println(req53);
        P.add(req53);

        Request req54 = new Request(54, 7, 1, 0 * 60 + 19, 0 * 60 + 44);
        //System.out.println(req54);
        P.add(req54);

        Request req55 = new Request(55, 10, 3, 0 * 60 + 51, 2 * 60 + 45);
        //System.out.println(req55);
        P.add(req55);

        Request req56 = new Request(56, 5, 2, 1 * 60 + 6, 2 * 60 + 9);
        //System.out.println(req56);
        P.add(req56);

        Request req57 = new Request(57, 7, 3, 0 * 60 + 16, 0 * 60 + 34);
        //System.out.println(req57);
        P.add(req57);

        Request req58 = new Request(58, 4, 1, 1 * 60 + 24, 1 * 60 + 49);
        //System.out.println(req58);
        P.add(req58);

        Request req59 = new Request(59, 7, 2, 1 * 60 + 21, 2 * 60 + 6);
        //System.out.println(req59);
        P.add(req59);

        Request req60 = new Request(60, 6, 1, 2 * 60 + 13, 2 * 60 + 31);
        //System.out.println(req60);
        P.add(req60);

        
         Request req61 = new Request(61,4,6,0*60+20,0*60+40);
         //System.out.println(req61);
         P.add(req61);

         Request req62 = new Request(62,9,1,2*60+24,2*60+57);
         //System.out.println(req62);
         P.add(req62);

         Request req63 = new Request(63,3,10,2*60+24,2*60+41);
         //System.out.println(req63);
         P.add(req63);

         Request req64 = new Request(64,5,9,2*60+39,2*60+57);
         //System.out.println(req64);
         P.add(req64);

         Request req65 = new Request(65,6,5,2*60+9,2*60+54);
         //System.out.println(req65);
         P.add(req65);

         Request req66 = new Request(66,5,6,0*60+43,1*60+50);
         //System.out.println(req66);
         P.add(req66);

         Request req67 = new Request(67,4,10,1*60+0,1*60+42);
         //System.out.println(req67);
         P.add(req67);

         Request req68 = new Request(68,1,11,2*60+47,2*60+59);
         //System.out.println(req68);
         P.add(req68);

         Request req69 = new Request(69,11,9,2*60+41,2*60+57);
         //System.out.println(req69);
         P.add(req69);

         Request req70 = new Request(70,9,6,0*60+57,1*60+6);
         //System.out.println(req70);
         P.add(req70);

         Request req71 = new Request(71,9,11,2*60+15,2*60+43);
         //System.out.println(req71);
         P.add(req71);

         Request req72 = new Request(72,2,4,0*60+28,1*60+46);
         //System.out.println(req72);
         P.add(req72);

         Request req73 = new Request(73,9,5,2*60+6,2*60+21);
         //System.out.println(req73);
         P.add(req73);

         Request req74 = new Request(74,2,10,0*60+45,2*60+40);
         //System.out.println(req74);
         P.add(req74);

         Request req75 = new Request(75,2,8,2*60+8,2*60+22);
         //System.out.println(req75);
         P.add(req75);

         Request req76 = new Request(76,3,10,0*60+4,2*60+41);
         //System.out.println(req76);
         P.add(req76);

         Request req77 = new Request(77,1,2,2*60+14,2*60+48);
         //System.out.println(req77);
         P.add(req77);

         Request req78 = new Request(78,10,6,2*60+17,2*60+56);
         //System.out.println(req78);
         P.add(req78);

         Request req79 = new Request(79,6,5,2*60+8,2*60+21);
         //System.out.println(req79);
         P.add(req79);

         Request req80 = new Request(80,10,3,1*60+11,2*60+42);
         //System.out.println(req80);
         P.add(req80);
		
         Request req81 = new Request(81,10,5,1*60+18,1*60+53);
         //System.out.println(req81);
         P.add(req81);

         Request req82 = new Request(82,4,8,0*60+21,0*60+46);
         //System.out.println(req82);
         P.add(req82);

         Request req83 = new Request(83,8,2,2*60+10,2*60+30);
         //System.out.println(req83);
         P.add(req83);

         Request req84 = new Request(84,5,8,0*60+13,1*60+48);
         //System.out.println(req84);
         P.add(req84);

         Request req85 = new Request(85,10,4,1*60+3,1*60+15);
         //System.out.println(req85);
         P.add(req85);

         Request req86 = new Request(86,6,9,2*60+14,2*60+28);
         //System.out.println(req86);
         P.add(req86);

         Request req87 = new Request(87,7,6,0*60+30,0*60+43);
         //System.out.println(req87);
         P.add(req87);

         Request req88 = new Request(88,1,4,0*60+6,1*60+18);
         //System.out.println(req88);
         P.add(req88);

         Request req89 = new Request(89,11,8,0*60+55,1*60+52);
         //System.out.println(req89);
         P.add(req89);

         Request req90 = new Request(90,8,3,0*60+20,1*60+21);
         //System.out.println(req90);
         P.add(req90);

         Request req91 = new Request(91,4,3,1*60+29,1*60+58);
         //System.out.println(req91);
         P.add(req91);

         Request req92 = new Request(92,8,10,2*60+4,2*60+58);
         //System.out.println(req92);
         P.add(req92);

         Request req93 = new Request(93,6,8,1*60+32,1*60+55);
         //System.out.println(req93);
         P.add(req93);

         Request req94 = new Request(94,6,5,2*60+41,2*60+58);
         //System.out.println(req94);
         P.add(req94);

         Request req95 = new Request(95,10,4,2*60+2,2*60+52);
         //System.out.println(req95);
         P.add(req95);

         Request req96 = new Request(96,1,8,2*60+15,2*60+30);
         //System.out.println(req96);
         P.add(req96);

         Request req97 = new Request(97,3,10,0*60+10,0*60+35);
         //System.out.println(req97);
         P.add(req97);

         Request req98 = new Request(98,6,5,1*60+1,2*60+55);
         //System.out.println(req98);
         P.add(req98);

         Request req99 = new Request(99,9,8,0*60+22,0*60+38);
         //System.out.println(req99);
         P.add(req99);

         Request req100 = new Request(100,9,6,2*60+19,2*60+37);
         //System.out.println(req100);
         P.add(req100);
		
         Request req101 = new Request(101,8,4,0*60+9,2*60+11);
         //System.out.println(req101);
         P.add(req101);

         Request req102 = new Request(102,7,10,0*60+35,1*60+18);
         //System.out.println(req102);
         P.add(req102);

         Request req103 = new Request(103,8,3,2*60+44,2*60+59);
         //System.out.println(req103);
         P.add(req103);

         Request req104 = new Request(104,3,5,1*60+42,2*60+54);
         //System.out.println(req104);
         P.add(req104);

         Request req105 = new Request(105,7,5,0*60+17,0*60+51);
         //System.out.println(req105);
         P.add(req105);

         Request req106 = new Request(106,2,1,0*60+25,0*60+55);
         //System.out.println(req106);
         P.add(req106);

         Request req107 = new Request(107,4,6,0*60+54,1*60+3);
         //System.out.println(req107);
         P.add(req107);

         Request req108 = new Request(108,10,4,2*60+2,2*60+47);
         //System.out.println(req108);
         P.add(req108);

         Request req109 = new Request(109,1,10,1*60+39,2*60+54);
         //System.out.println(req109);
         P.add(req109);

         Request req110 = new Request(110,9,10,0*60+8,1*60+33);
         //System.out.println(req110);
         P.add(req110);

         Request req111 = new Request(111,8,1,1*60+6,2*60+37);
         //System.out.println(req111);
         P.add(req111);

         Request req112 = new Request(112,1,11,1*60+32,2*60+44);
         //System.out.println(req112);
         P.add(req112);

         Request req113 = new Request(113,8,3,2*60+8,2*60+58);
         //System.out.println(req113);
         P.add(req113);

         Request req114 = new Request(114,7,5,0*60+14,0*60+52);
         //System.out.println(req114);
         P.add(req114);

         Request req115 = new Request(115,7,9,0*60+9,0*60+32);
         //System.out.println(req115);
         P.add(req115);

         Request req116 = new Request(116,7,3,1*60+0,1*60+27);
         //System.out.println(req116);
         P.add(req116);

         Request req117 = new Request(117,11,8,0*60+10,0*60+41);
         //System.out.println(req117);
         P.add(req117);

         Request req118 = new Request(118,8,5,0*60+19,2*60+36);
         //System.out.println(req118);
         P.add(req118);

         Request req119 = new Request(119,4,5,0*60+20,0*60+34);
         //System.out.println(req119);
         P.add(req119);

         Request req120 = new Request(120,2,7,0*60+14,0*60+42);
         //System.out.println(req120);
         P.add(req120);
		
         Request req121 = new Request(121,4,8,2*60+31,2*60+46);
         //System.out.println(req121);
         P.add(req121);

         Request req122 = new Request(122,5,9,2*60+10,2*60+22);
         //System.out.println(req122);
         P.add(req122);

         Request req123 = new Request(123,7,5,1*60+8,1*60+25);
         //System.out.println(req123);
         P.add(req123);

         Request req124 = new Request(124,11,5,0*60+38,1*60+39);
         //System.out.println(req124);
         P.add(req124);

         Request req125 = new Request(125,2,11,2*60+16,2*60+32);
         //System.out.println(req125);
         P.add(req125);

         Request req126 = new Request(126,6,3,1*60+45,2*60+2);
         //System.out.println(req126);
         P.add(req126);

         Request req127 = new Request(127,5,3,2*60+14,2*60+28);
         //System.out.println(req127);
         P.add(req127);

         Request req128 = new Request(128,4,11,2*60+8,2*60+26);
         //System.out.println(req128);
         P.add(req128);

         Request req129 = new Request(129,3,11,0*60+20,2*60+35);
         //System.out.println(req129);
         P.add(req129);

         Request req130 = new Request(130,4,3,0*60+30,1*60+19);
         //System.out.println(req130);
         P.add(req130);

         Request req131 = new Request(131,7,4,1*60+49,2*60+37);
         //System.out.println(req131);
         P.add(req131);

         Request req132 = new Request(132,11,9,1*60+0,1*60+21);
         //System.out.println(req132);
         P.add(req132);

         Request req133 = new Request(133,11,8,1*60+15,2*60+26);
         //System.out.println(req133);
         P.add(req133);

         Request req134 = new Request(134,5,8,0*60+49,2*60+50);
         //System.out.println(req134);
         P.add(req134);

         Request req135 = new Request(135,7,11,0*60+14,0*60+36);
         //System.out.println(req135);
         P.add(req135);

         Request req136 = new Request(136,2,3,1*60+6,1*60+24);
         //System.out.println(req136);
         P.add(req136);

         Request req137 = new Request(137,11,8,1*60+23,1*60+35);
         //System.out.println(req137);
         P.add(req137);

         Request req138 = new Request(138,9,8,1*60+47,1*60+59);
         //System.out.println(req138);
         P.add(req138);

         Request req139 = new Request(139,1,8,1*60+10,1*60+24);
         //System.out.println(req139);
         P.add(req139);

         Request req140 = new Request(140,4,6,2*60+23,2*60+47);
         //System.out.println(req140);
         P.add(req140);

         Request req141 = new Request(141,2,3,0*60+39,1*60+24);
         //System.out.println(req141);
         P.add(req141);

         Request req142 = new Request(142,5,2,0*60+14,0*60+57);
         //System.out.println(req142);
         P.add(req142);

         Request req143 = new Request(143,5,7,2*60+4,2*60+50);
         //System.out.println(req143);
         P.add(req143);

         Request req144 = new Request(144,8,4,1*60+5,1*60+27);
         //System.out.println(req144);
         P.add(req144);

         Request req145 = new Request(145,3,7,1*60+53,2*60+41);
         //System.out.println(req145);
         P.add(req145);

         Request req146 = new Request(146,1,2,1*60+22,2*60+0);
         //System.out.println(req146);
         P.add(req146);

         Request req147 = new Request(147,11,9,2*60+16,2*60+30);
         //System.out.println(req147);
         P.add(req147);

         Request req148 = new Request(148,1,3,0*60+26,0*60+48);
         //System.out.println(req148);
         P.add(req148);

         Request req149 = new Request(149,8,4,2*60+12,2*60+31);
         //System.out.println(req149);
         P.add(req149);

         Request req150 = new Request(150,11,6,1*60+53,2*60+18);
         //System.out.println(req150);
         P.add(req150);

         Request req151 = new Request(151,11,10,1*60+22,1*60+42);
         //System.out.println(req151);
         P.add(req151);

         Request req152 = new Request(152,9,5,1*60+0,1*60+18);
         //System.out.println(req152);
         P.add(req152);

         Request req153 = new Request(153,11,4,1*60+27,2*60+10);
         //System.out.println(req153);
         P.add(req153);

         Request req154 = new Request(154,7,1,1*60+45,2*60+54);
         //System.out.println(req154);
         P.add(req154);

         Request req155 = new Request(155,1,6,1*60+56,2*60+33);
         //System.out.println(req155);
         P.add(req155);

         Request req156 = new Request(156,2,9,2*60+20,2*60+37);
         //System.out.println(req156);
         P.add(req156);

         Request req157 = new Request(157,3,10,2*60+6,2*60+20);
         //System.out.println(req157);
         P.add(req157);

         Request req158 = new Request(158,10,6,1*60+28,2*60+46);
         //System.out.println(req158);
         P.add(req158);

         Request req159 = new Request(159,9,1,1*60+25,2*60+1);
         //System.out.println(req159);
         P.add(req159);

         Request req160 = new Request(160,7,2,0*60+25,1*60+17);
         //System.out.println(req160);
         P.add(req160);

         Request req161 = new Request(161,10,11,1*60+10,1*60+54);
         //System.out.println(req161);
         P.add(req161);

         Request req162 = new Request(162,2,8,0*60+15,2*60+58);
         //System.out.println(req162);
         P.add(req162);

         Request req163 = new Request(163,7,1,0*60+42,2*60+8);
         //System.out.println(req163);
         P.add(req163);

         Request req164 = new Request(164,2,4,2*60+27,2*60+50);
         //System.out.println(req164);
         P.add(req164);

         Request req165 = new Request(165,3,7,0*60+44,1*60+55);
         //System.out.println(req165);
         P.add(req165);

         Request req166 = new Request(166,10,4,2*60+11,2*60+42);
         //System.out.println(req166);
         P.add(req166);

         Request req167 = new Request(167,7,2,2*60+27,2*60+42);
         //System.out.println(req167);
         P.add(req167);

         Request req168 = new Request(168,4,8,2*60+3,2*60+34);
         //System.out.println(req168);
         P.add(req168);

         Request req169 = new Request(169,2,9,2*60+12,2*60+41);
         //System.out.println(req169);
         P.add(req169);

         Request req170 = new Request(170,1,11,1*60+42,2*60+38);
         //System.out.println(req170);
         P.add(req170);

         Request req171 = new Request(171,2,3,1*60+3,1*60+47);
         //System.out.println(req171);
         P.add(req171);

         Request req172 = new Request(172,10,8,0*60+47,2*60+6);
         //System.out.println(req172);
         P.add(req172);

         Request req173 = new Request(173,6,2,0*60+18,2*60+18);
         //System.out.println(req173);
         P.add(req173);

         Request req174 = new Request(174,9,11,2*60+45,2*60+59);
         //System.out.println(req174);
         P.add(req174);

         Request req175 = new Request(175,11,3,2*60+17,2*60+53);
         //System.out.println(req175);
         P.add(req175);

         Request req176 = new Request(176,5,1,0*60+42,2*60+44);
         //System.out.println(req176);
         P.add(req176);

         Request req177 = new Request(177,1,4,1*60+47,2*60+31);
         //System.out.println(req177);
         P.add(req177);

         Request req178 = new Request(178,5,1,2*60+1,2*60+53);
         //System.out.println(req178);
         P.add(req178);

         Request req179 = new Request(179,5,9,1*60+15,1*60+34);
         //System.out.println(req179);
         P.add(req179);

         Request req180 = new Request(180,4,7,2*60+7,2*60+37);
         //System.out.println(req180);
         P.add(req180);

         Request req181 = new Request(181,1,7,1*60+45,2*60+3);
         //System.out.println(req181);
         P.add(req181);

         Request req182 = new Request(182,9,11,0*60+0,0*60+27);
         //System.out.println(req182);
         P.add(req182);

         Request req183 = new Request(183,4,5,1*60+2,1*60+34);
         //System.out.println(req183);
         P.add(req183);

         Request req184 = new Request(184,5,7,0*60+3,2*60+52);
         //System.out.println(req184);
         P.add(req184);

         Request req185 = new Request(185,9,2,0*60+37,2*60+20);
         //System.out.println(req185);
         P.add(req185);

         Request req186 = new Request(186,3,5,2*60+9,2*60+33);
         //System.out.println(req186);
         P.add(req186);

         Request req187 = new Request(187,1,8,2*60+14,2*60+39);
         //System.out.println(req187);
         P.add(req187);

         Request req188 = new Request(188,7,2,1*60+35,2*60+12);
         //System.out.println(req188);
         P.add(req188);

         Request req189 = new Request(189,2,5,1*60+7,1*60+29);
         //System.out.println(req189);
         P.add(req189);

         Request req190 = new Request(190,5,10,0*60+0,0*60+27);
         //System.out.println(req190);
         P.add(req190);

         Request req191 = new Request(191,2,5,0*60+10,0*60+42);
         //System.out.println(req191);
         P.add(req191);

         Request req192 = new Request(192,4,9,1*60+43,2*60+10);
         //System.out.println(req192);
         P.add(req192);

         Request req193 = new Request(193,1,5,1*60+23,2*60+34);
         //System.out.println(req193);
         P.add(req193);

         Request req194 = new Request(194,8,6,0*60+25,0*60+44);
         //System.out.println(req194);
         P.add(req194);

         Request req195 = new Request(195,5,2,0*60+10,1*60+18);
         //System.out.println(req195);
         P.add(req195);

         Request req196 = new Request(196,9,7,2*60+14,2*60+42);
         //System.out.println(req196);
         P.add(req196);

         Request req197 = new Request(197,6,11,1*60+11,1*60+34);
         //System.out.println(req197);
         P.add(req197);

         Request req198 = new Request(198,10,6,1*60+14,2*60+27);
         //System.out.println(req198);
         P.add(req198);

         Request req199 = new Request(199,1,6,1*60+28,1*60+40);
         //System.out.println(req199);
         P.add(req199);

         Request req200 = new Request(200,10,9,1*60+8,2*60+35);
         //System.out.println(req200);
         P.add(req200);
		
          /**
         * FIM - SOLICITA��ES GERADAS ALEATORIAMENTE *
         */

        /**
         * Iniciando dados do problema - Pmais (todas origens), Pmenos (todos
         * destinos)
         */
        Pmais.clear();
        Pmenos.clear();

        //Adiciona origens e destinos
        for (Request request : P) {
            Pmais.add(request.getOrigin());
            Pmenos.add(request.getDestination());
        }

        /**
         * Iniciando dados do problema - V (conj. n�s de parada)
         */
        V.clear();

        V.add(0);
        V.addAll(Pmais);
        V.addAll(Pmenos);

        /**
         * Iniciando dados do problema - Pin e Pout
         */
        Pin.clear();
        Pout.clear();
        /**
         * Usar m�todo clone()*
         */
        List<Request> origem = new LinkedList<Request>();
        List<Request> destino = new LinkedList<Request>();
        for (int j = 0; j < n; j++) {
            for (Request request : P) {
                if (request.getOrigin() == j) {
                    origem.add((Request) request.clone());
                }
                if (request.getDestination() == j) {
                    destino.add((Request) request.clone());
                }
            }

            Pin.put(j, new LinkedList<Request>(origem));
            Pout.put(j, new LinkedList<Request>(destino));

            origem.clear();
            destino.clear();
        }
        /*//Impressao Pin e Pout
         System.out.println("\nPIN");
         for (Map.Entry<Integer, List<Request>> e : Pin.entrySet()) {
         Integer key = e.getKey();
         List<Request> value =  e.getValue(); 

         String s = "V"+key+" ";
         for(Request request : value)
         s += request+" ";
         System.out.println(s);
         }

         System.out.println("\nPOUT");
         for (Map.Entry<Integer, List<Request>> e : Pout.entrySet()) {
         Integer key = e.getKey();
         List<Request> value =  e.getValue(); 

         String s = "V"+key+" ";
         for(Request request : value)
         s += request+" ";
         System.out.println(s);
         }
         System.out.println("");*/
        /**
         * Iniciando dados do problema - m
         */
        m.clear();

        for (int i = 0; i < n; i++) {
            m.add(Pin.get(i).size() - Pout.get(i).size());
        }

        //System.out.println(m);
        //System.out.println("Fazendo teste de leitura das requisções: P.size() = " + P.size());
    }

    public static void carregaAdjacencia(List<List<Integer>> la, List<List<Integer>> c, List<List<Integer>> d, Integer n) {
        Integer INFINITO = 999999;

        for (int i = 0; i < n; i++) {
            la.add(new LinkedList<Integer>());

            c.add(new LinkedList<Integer>());

            d.add(new LinkedList<Integer>());

            for (int j = 0; j < n; j++) {
                if (i != j) {
                    c.get(i).add(INFINITO);
                    d.get(i).add(INFINITO);
                } else {
                    c.get(i).add(0);
                    d.get(i).add(0);
                }
            }
        }

        List<Integer> linhaLA;
        linhaLA = new LinkedList<Integer>();

        //linha 0
        linhaLA.add(1);

        la.get(0).addAll(linhaLA);
        c.get(0).set(1, 4);
        d.get(0).set(1, 4);

        linhaLA.clear();

        //linha 1
        linhaLA.add(0);
        c.get(1).set(0, 4);
        d.get(1).set(0, 4);

        linhaLA.add(2);
        c.get(1).set(2, 5);
        d.get(1).set(2, 5);

        linhaLA.add(3);
        c.get(1).set(3, 10);
        d.get(1).set(3, 10);

        linhaLA.add(5);
        c.get(1).set(5, 12);
        d.get(1).set(5, 12);

        linhaLA.add(11);
        c.get(1).set(11, 8);
        d.get(1).set(11, 8);

        la.get(1).addAll(linhaLA);

        linhaLA.clear();

        //linha 2
        linhaLA.add(1);
        c.get(2).set(1, 5);
        d.get(2).set(1, 5);

        linhaLA.add(3);
        c.get(2).set(3, 7);
        d.get(2).set(3, 7);

        la.get(2).addAll(linhaLA);

        linhaLA.clear();

        //linha 3
        linhaLA.add(1);
        c.get(3).set(1, 10);
        d.get(3).set(1, 10);

        linhaLA.add(2);
        c.get(3).set(2, 7);
        d.get(3).set(2, 7);

        linhaLA.add(4);
        c.get(3).set(4, 6);
        d.get(3).set(4, 6);

        la.get(3).addAll(linhaLA);

        linhaLA.clear();

        //linha 4
        linhaLA.add(3);
        c.get(4).set(3, 6);
        d.get(4).set(3, 6);

        linhaLA.add(5);
        c.get(4).set(5, 3);
        d.get(4).set(5, 3);

        la.get(4).addAll(linhaLA);

        linhaLA.clear();

        //linha 5
        linhaLA.add(1);
        c.get(5).set(1, 12);
        d.get(5).set(1, 12);

        linhaLA.add(4);
        c.get(5).set(4, 3);
        d.get(5).set(4, 3);

        linhaLA.add(6);
        c.get(5).set(6, 8);
        d.get(5).set(6, 8);

        la.get(5).addAll(linhaLA);

        linhaLA.clear();

        //linha 6
        linhaLA.add(5);
        c.get(6).set(5, 8);
        d.get(6).set(5, 8);

        linhaLA.add(7);
        c.get(6).set(7, 5);
        d.get(6).set(7, 5);

        la.get(6).addAll(linhaLA);

        linhaLA.clear();

        //linha 7
        linhaLA.add(6);
        c.get(7).set(6, 5);
        d.get(7).set(6, 5);

        linhaLA.add(8);
        c.get(7).set(8, 2);
        d.get(7).set(8, 2);

        la.get(7).addAll(linhaLA);

        linhaLA.clear();

        //linha 8
        linhaLA.add(7);
        c.get(8).set(7, 2);
        d.get(8).set(7, 2);

        linhaLA.add(9);
        c.get(8).set(9, 3);
        d.get(8).set(9, 3);

        la.get(8).addAll(linhaLA);

        linhaLA.clear();

        //linha 9
        linhaLA.add(8);
        c.get(9).set(8, 3);
        d.get(9).set(8, 3);

        linhaLA.add(10);
        c.get(9).set(10, 7);
        d.get(9).set(10, 7);

        la.get(9).addAll(linhaLA);

        linhaLA.clear();

        //linha 10
        linhaLA.add(9);
        c.get(10).set(9, 7);
        d.get(10).set(9, 7);

        linhaLA.add(11);
        c.get(10).set(11, 6);
        d.get(10).set(11, 6);

        la.get(10).addAll(linhaLA);

        linhaLA.clear();

        //linha 11
        linhaLA.add(1);
        c.get(11).set(1, 8);
        d.get(11).set(1, 8);

        linhaLA.add(10);
        c.get(11).set(10, 6);
        d.get(11).set(10, 6);

        la.get(11).addAll(linhaLA);

        linhaLA.clear();
    }

    public static void SeparaOrigemDestino(List<Request> U, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout, Integer n, List<Request> P) {
        //--------------------------------------------------------------------------------------------------------------------------------------
        U.clear();
        Pin.clear();
        Pout.clear();

        List<Request> origem = new LinkedList<Request>();
        List<Request> destino = new LinkedList<Request>();

        for (int j = 0; j < n; j++) {
            for (Request request : P) {
                if ((request.getOrigin() == j || request.getDestination() == j)) {
                    if (request.getOrigin() == j) {
                        origem.add((Request) request.clone());
                    } else {
                        destino.add((Request) request.clone());
                    }
                }
            }

            Pin.put(j, new LinkedList<Request>(origem));
            Pout.put(j, new LinkedList<Request>(destino));

            origem.clear();
            destino.clear();
        }
    }

    public static void EncontraNosViaveis(Integer n, Integer lastNode, boolean encontrado, Integer Qmax, Rota R, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout, Set<Integer> FeasibleNode,
            List<List<Integer>> d, Integer currentTime, Integer TimeWindows) {
        //--------------------------------------------------------------------------------------------------------------------------------------
        for (int i = 1; i < n; i++) {
            CondicionaisViabilidade(i, lastNode, encontrado, R, Qmax, Pin, Pout, FeasibleNode, d, currentTime, TimeWindows);
        }
    }

    public static void CalculaCRL(Set<Integer> FeasibleNode, Map<Integer, Double> CRL, List<List<Integer>> c, Integer lastNode) {
        //--------------------------------------------------------------------------------------------------------------------------------------
        Double min, max;
        for (Integer i : FeasibleNode) {
            CRL.put(i, (double) c.get(lastNode).get(i));
        }

        //Normalizacao
        min = Collections.min(CRL.values());
        max = Collections.max(CRL.values());

        if (min != max) {
            for (Integer i : FeasibleNode) {
                CRL.put(i, ((max - CRL.get(i)) / (max - min)));
            }
        } else {
            for (Integer i : FeasibleNode) {
                CRL.put(i, 1.0);
            }
        }

    }

    public static void CalculaNRL(Set<Integer> FeasibleNode, Map<Integer, Double> NRL, List<Integer> m, Integer lastNode) {
        //--------------------------------------------------------------------------------------------------------------------------------------
        Double min, max;
        for (Integer i : FeasibleNode) {
            NRL.put(i, (double) m.get(i));
        }

        //Normalizacao
        min = Collections.min(NRL.values());
        max = Collections.max(NRL.values());

        if (min != max) {
            for (Integer i : FeasibleNode) {
                NRL.put(i, ((NRL.get(i) - min) / (max - min)));
            }
        } else {
            for (Integer i : FeasibleNode) {
                NRL.put(i, 1.0);
            }
        }
    }

    public static void CalculaDRL(Set<Integer> FeasibleNode, Map<Integer, Double> DRL, Map<Integer, List<Request>> Pout,
            Integer lastNode, List<List<Integer>> d, List<Integer> EarliestTime) {
        //--------------------------------------------------------------------------------------------------------------------------------------
        Double min, max;
        for (Integer i : FeasibleNode) {
            if (Pout.get(i).size() > 0) {
                for (Request request : Pout.get(i)) {
                    EarliestTime.add(request.getDeliveryE());
                }
                DRL.put(i, (double) (Collections.min(EarliestTime) + d.get(lastNode).get(i)));
                EarliestTime.clear();
            }
        }

        //Normalizacao
        if (DRL.size() > 0) {
            min = Collections.min(DRL.values());
            max = Collections.max(DRL.values());

            if (min != max) {
                for (Integer i : DRL.keySet()) {
                    DRL.put(i, ((max - DRL.get(i)) / (max - min)));
                }
            } else {
                for (Integer i : DRL.keySet()) {
                    DRL.put(i, 1.0);
                }
            }
        }

        for (Integer i : FeasibleNode) {
            if (!DRL.containsKey(i)) {
                DRL.put(i, 0.0);
            }
        }
    }

    public static void CalculaTRL(Set<Integer> FeasibleNode, Map<Integer, Double> TRL, Map<Integer, List<Request>> Pin,
            Integer lastNode, List<List<Integer>> d, List<Integer> EarliestTime) {
        //--------------------------------------------------------------------------------------------------------------------------------------
        double min, max;
        for (Integer i : FeasibleNode) {
            if (Pin.get(i).size() > 0) {
                for (Request request : Pin.get(i)) {
                    EarliestTime.add(request.getPickupE());
                }

                TRL.put(i, (double) (Collections.min(EarliestTime) + d.get(lastNode).get(i)));

                EarliestTime.clear();
            }
        }

        //Normalizacao
        if (TRL.size() > 0) {
            min = Collections.min(TRL.values());
            max = Collections.max(TRL.values());

            if (min != max) {
                for (Integer i : TRL.keySet()) {
                    TRL.put(i, ((max - TRL.get(i)) / (max - min)));
                }
            } else {
                for (Integer i : TRL.keySet()) {
                    TRL.put(i, 1.0);
                }
            }
        }

        for (Integer i : FeasibleNode) {
            if (!TRL.containsKey(i)) {
                TRL.put(i, 0.0);
            }
        }
    }

    public static void CalculaListaSemNosViaveis(Map<Integer, Double> lista, Set<Integer> FeasibleNode) {
        //--------------------------------------------------------------------------------------------------------------------------------------
        for (Integer i : FeasibleNode) {
            lista.put(i, 1.0);
        }
    }

    public static void CalculaNRF(Map<Integer, Double> NRF, Map<Integer, Double> CRL, Map<Integer, Double> NRL,
            Map<Integer, Double> DRL, Map<Integer, Double> TRL, Double alphaD, Double alphaP,
            Double alphaV, Double alphaT, Set<Integer> FeasibleNode) {
        //--------------------------------------------------------------------------------------------------------------------------------------
        for (Integer i : FeasibleNode) {
            NRF.put(i, (alphaD * CRL.get(i) + alphaP * NRL.get(i))
                    + (alphaV * DRL.get(i) + alphaT * TRL.get(i)));
        }
    }

    public static int AdicionaNo(Map<Integer, Double> NRF, Map<Integer, Double> CRL, Map<Integer, Double> NRL, Map<Integer, Double> DRL,
            Map<Integer, Double> TRL, Double max, Integer lastNode, Map<Integer, List<Request>> Pin,
            List<List<Integer>> d, List<Integer> EarliestTime, Integer currentTime, Rota R) {
        //-------------------------------------------------------------------------------------------------------------------------------------- 
        for (Map.Entry<Integer, Double> e : NRF.entrySet()) {
            Integer newNode = e.getKey();
            Double value = e.getValue();

            if (Objects.equals(max, value)) {
                if (lastNode == 0) {
                    for (Request request : Pin.get(newNode)) {
                        if (d.get(lastNode).get(newNode) <= request.getPickupL()) {
                            EarliestTime.add(request.getPickupE());
                        }
                    }
                    currentTime = Math.max(Collections.min(EarliestTime) - d.get(lastNode).get(newNode), 0);
                    R.setTempoikDeposito(currentTime);
                    EarliestTime.clear();
                }

                currentTime += d.get(lastNode).get(newNode);

                R.addVisitacao(newNode);
                lastNode = R.getLastNode();
                break;
            }
        }
        CRL.clear();
        NRL.clear();
        DRL.clear();
        TRL.clear();
        NRF.clear();
        return currentTime;
    }

    public static void Desembarca(Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout, Integer lastNode, Integer currentTime,
            List<Request> P, List<Request> listRequestAux, Rota R, String log) {
        //--------------------------------------------------------------------------------------------------------------------------------------
        listRequestAux.addAll(Pout.get(lastNode));//percorre todas as solicitações que desembarcam no último nó

        for (Request request : listRequestAux) {

            if (!Pin.get(request.getOrigin()).contains(request)) {
                Pout.get(lastNode).remove((Request) request.clone());
                P.remove((Request) request.clone());
                log += "ENTREGA: " + currentTime + ": " + (Request) request.clone() + " ";
                try {
                    R.addDesembarque((Request) request.clone(), currentTime);
                } catch (Exception e) {
                    //System.out.print("solucao vigente: " + S + " R problema\n");
                    System.out.println("L Atend (" + R.getListaAtendimento().size() + ") " + R.getListaAtendimento());
                    System.out.println("L Visit (" + R.getListaVisitacao().size() + ") " + R.getListaVisitacao());
                    System.out.println("Qik (" + R.getQik().size() + ") " + R.getQik());
                    System.out.println("Tempoik (" + R.getTempoik().size() + ") " + R.getTempoik());
                    System.exit(-1);
                }
                //EXTRA
                log += "Q=" + R.getLotacaoAtual() + " ";
            }
        }
        listRequestAux.clear();
    }

    public static void Embarca(Map<Integer, List<Request>> Pin, Integer lastNode, Integer currentTime,
            List<Request> P, List<Request> listRequestAux, Rota R, String log, Integer Qmax) {
        //--------------------------------------------------------------------------------------------------------------------------------------
        listRequestAux.addAll(Pin.get(lastNode));

        for (Request request : listRequestAux) {
            if (R.getLotacaoAtual() < Qmax && currentTime >= request.getPickupE() && currentTime <= request.getPickupL()) {
                Pin.get(lastNode).remove((Request) request.clone());
                log += "COLETA: " + currentTime + ": " + (Request) request.clone() + " ";
                R.addEmbarque((Request) request.clone(), currentTime);
                //EXTRA
                log += "Q =" + R.getLotacaoAtual() + " ";
            }
        }

        listRequestAux.clear();
    }

    public static int EmbarcaRelaxacao(Map<Integer, List<Request>> Pin, Integer lastNode, Integer currentTime, List<Request> P,
            List<Request> listRequestAux, Rota R, String log, Integer Qmax, Integer TimeWindows) {
        //--------------------------------------------------------------------------------------------------------------------------------------
        listRequestAux.addAll(Pin.get(lastNode));

        int waitTime = TimeWindows;
        int aux;

        for (Request request : listRequestAux) {
            if (R.getLotacaoAtual() < Qmax && currentTime + waitTime >= request.getPickupE() && currentTime + waitTime <= request.getPickupL()) {

                aux = currentTime + waitTime - request.getPickupE();
                currentTime = Math.min(currentTime + waitTime, request.getPickupE());
                waitTime = aux;

                Pin.get(lastNode).remove((Request) request.clone());

                log += "COLETAw: " + currentTime + ": " + (Request) request.clone() + " ";

                R.addEmbarque((Request) request.clone(), currentTime);

                //EXTRA
                log += "Q=" + R.getLotacaoAtual() + " ";
            }
        }
        return currentTime;
    }

    public static void RetiraSolicitacoesInviaveis(Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout, List<Request> listRequestAux,
            Integer currentTime, List<Request> P, List<Request> U) {
        //--------------------------------------------------------------------------------------------------------------------------------------
        listRequestAux.clear();
        for (Integer key : Pin.keySet()) {
            listRequestAux.addAll(Pin.get(key));
            Integer i;
            Integer n2;
            for (i = 0, n2 = listRequestAux.size(); i < n2; i++) {//percorre todas as requisições que embarcariam em lastNode
                Request request = listRequestAux.get(i);
                if (currentTime > request.getPickupL()) {
                    U.add((Request) request.clone());
                    P.remove((Request) request.clone());
                    Pin.get(key).remove((Request) request.clone());
                    Pout.get(request.getDestination()).remove((Request) request.clone());
                }
            }
            listRequestAux.clear();
        }
    }

    public static boolean ProcuraSolicitacaoParaAtender(Rota R, Integer Qmax, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout,
            Integer currentTime, Integer n, List<List<Integer>> d, Integer lastNode,
            Integer TimeWindows, boolean encontrado) {
        //-------------------------------------------------------------------------------------------------------------------------------------- 
        encontrado = false;
        for (int i = 1; !encontrado && i < n; i++) {//varre todas as solicitações para encontrar se tem alguma viável
            if (i != lastNode) {

                //Procura solicitação para embarcar
                if (R.getLotacaoAtual() < Qmax) {//se tiver lugar, ele tenta embarcar alguem no veículo
                    for (Request request : Pin.get(i)) {//percorre todos os nós menos o nó que acabou de ser adicionado (por causa da restrição)
                        if (currentTime + d.get(lastNode).get(i) >= request.getPickupE() - TimeWindows
                                && currentTime + d.get(lastNode).get(i) <= request.getPickupL()) {
                            encontrado = true;
                            break;
                        }
                    }
                }
                //Procura solicitação para desembarcar
                if (!encontrado && R.getLotacaoAtual() > 0) {
                    for (Request request : Pout.get(i)) {
                        if (!Pin.get(request.getOrigin()).contains(request)) {
                            encontrado = true;
                            break;
                        }
                    }
                }
            }
        }
        return encontrado;
    }

    public static void RetiraSolicitacaoNaoSeraAtendida(boolean encontrado, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout, List<Request> listRequestAux,
            Integer currentTime, List<Request> P, List<Request> U) {
        //--------------------------------------------------------------------------------------------------------------------------------------         
        if (!encontrado) {
            for (Integer key : Pin.keySet()) {//bloco de comando que coloca as solicitações que nn embarcaram no conjunto de inviáveis (U)
                listRequestAux.addAll(Pin.get(key));
                Integer i, n2;
                for (i = 0, n2 = listRequestAux.size(); i < n2; i++) {
                    Request request = listRequestAux.get(i);
                    U.add((Request) request.clone());
                    P.remove((Request) request.clone());
                    Pin.get(key).remove((Request) request.clone());
                    Pout.get(request.getDestination()).remove((Request) request.clone());
                }
                listRequestAux.clear();
            }
        }
    }

    public static int FinalizaRota(List<Request> P, Rota R, Integer currentTime, Integer lastNode, List<List<Integer>> d, Solucao S) {
        //-------------------------------------------------------------------------------------------------------------------------------------- 
        if (P.isEmpty()) {
            R.addVisitacao(0);
            currentTime += d.get(lastNode).get(0);
            S.getConjRotas().add(R);
        }
        return currentTime;
    }

    public static void AnaliseSolicitacoesViaveisEmU(List<Request> U, List<Request> P, Iterator itK, List<List<Integer>> d) {
        //-------------------------------------------------------------------------------------------------------------------------------------- 
        if (!U.isEmpty() && itK.hasNext()) {//analise se há solicitações que possam ser atendidas com um novo veículo começando na origem
            boolean encontrado = false;
            List<Request> auxU = new ArrayList<Request>(U);
            for (Request request : auxU) {
                if (d.get(0).get(request.getOrigin()) <= request.getPickupL()) {
                    encontrado = true;
                }
            }
            if (encontrado) {
                P.addAll(U);
            }
        }
    }

    public static void CondicionaisViabilidade(Integer i, Integer lastNode, boolean encontrado, Rota R, Integer Qmax, Map<Integer, List<Request>> Pin,
            Map<Integer, List<Request>> Pout, Set<Integer> FeasibleNode, List<List<Integer>> d, Integer currentTime,
            Integer TimeWindows) {
        if (i != lastNode) {
            encontrado = false;
            if (R.getLotacaoAtual() < Qmax) {
                for (Request request : Pin.get(i)) {//retorna uma lista com as requisições que embarcam em i
                    if (lastNode == 0 && d.get(lastNode).get(i) <= request.getPickupL()) { //d.get(lastNode).get(i) � o tempo de chegar de lastNode ate o no i?
                        FeasibleNode.add(i);
                        encontrado = true;
                        break;
                    }
                    //para lastNode que não seja a origem - faz cair dentro da janela de tempo de pelo menos uma requisição
                    if (!encontrado && currentTime + d.get(lastNode).get(i) >= request.getPickupE() - TimeWindows
                            && currentTime + d.get(lastNode).get(i) <= request.getPickupL()) {
                        FeasibleNode.add(i);
                        encontrado = true;
                        break;
                    }
                }
            }

            /**
             * E OS NÓS DE ENTREGA? DEVEM SER VIÁVEIS TAMBÉM?*
             */
            if (!encontrado && R.getLotacaoAtual() > 0) {//ou seja, se não encontrou um nó viavel e há pessoas dentro do veículo
                for (Request request : Pout.get(i)) {//retorna uma lista com as requisições que desembarcam em i
                    if (!Pin.get(request.getOrigin()).contains(request)) {
                        FeasibleNode.add(i);
                        break;
                    }
                }
            }
        }
    }

    public static void InicializaPopulacao(List<Solucao> Pop, Integer TamPop, List<Request> listRequests, Map<Integer, List<Request>> Pin,
            Map<Integer, List<Request>> Pout, Integer n, Integer Qmax, Set<Integer> K, List<Request> U,
            List<Request> P, List<Integer> m, List<List<Integer>> d, List<List<Integer>> c,
            Integer TimeWindows, Integer currentTime, Integer lastNode) {

        for (int i = 0; i < TamPop; i++) {
            Solucao S = new Solucao();
            S.setSolucao(GeraSolucaoAleatoria(Pop, TamPop, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode));
            Pop.add(S);
        }

        for (int i = 0; i < TamPop; i++) {
            Solucao solucao = new Solucao(Pop.get(i));
        }
        //Coloquei a linha de baixo, que estava no codigo principal dos algoritmos multi
        Inicializa(Pop, TamPop, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
    }
    
    
    public static void InicializaPopulacaoPerturbacao(List<Solucao> Pop, Integer TamPop, List<Request> listRequests, Map<Integer, List<Request>> Pin,
            Map<Integer, List<Request>> Pout, Integer n, Integer Qmax, Set<Integer> K, List<Request> U,
            List<Request> P, List<Integer> m, List<List<Integer>> d, List<List<Integer>> c,
            Integer TimeWindows, Integer currentTime, Integer lastNode) {

        for (int i = 0; i < TamPop; i++) {
            Solucao S = new Solucao();
            Solucao S_linha = new Solucao();
            S.setSolucao(GeraSolucaoAleatoria(Pop, TamPop, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode));
            S_linha.setSolucao(Perturbacao(S, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows));
            Pop.add(S_linha);
        }

//        for (int i = 0; i < TamPop; i++) {
//            Solucao solucao = new Solucao(Pop.get(i));
//        }
//        //Coloquei a linha de baixo, que estava no codigo principal dos algoritmos multi
//        Inicializa(Pop, TamPop, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode);
    }
    
    public static void InicializaPopulacaoGulosa(List<Solucao> Pop, Integer TamPop, List<Request> listRequests, Map<Integer, List<Request>> Pin,
            Map<Integer, List<Request>> Pout, Integer n, Integer Qmax, Set<Integer> K, List<Request> U,
            List<Request> P, List<Integer> m, List<List<Integer>> d, List<List<Integer>> c,
            Integer TimeWindows, Integer currentTime, Integer lastNode) {

        for (int i = 0; i < TamPop; i++) {
            Solucao S = new Solucao();
            Random rnd = new Random();
            double x, y, z, w;
            do {
                x = rnd.nextDouble();
                y = rnd.nextDouble();
                z = rnd.nextDouble();
                w = 1 - x - y - z;
            } while (x + y + z > 1);
            S.setSolucao(greedyConstructive(x, y, z, w, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode));
            //System.out.println("SolucaoGulosaAleatoria = "+ S);
            //S.setSolucao(GeraSolucaoAleatoria(Pop, TamPop, listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows, currentTime, lastNode));
            Pop.add(S);

        }
        //for (int i = 0; i < TamPop; i++) {
        //  Solucao solucao = new Solucao(Pop.get(i));
        //}
    }

    public static Solucao GeraSolucaoAleatoria(List<Solucao> Pop, Integer TamPop, List<Request> listRequests, Map<Integer, List<Request>> Pin,
            Map<Integer, List<Request>> Pout, Integer n, Integer Qmax, Set<Integer> K, List<Request> U,
            List<Request> P, List<Integer> m, List<List<Integer>> d, List<List<Integer>> c,
            Integer TimeWindows, Integer currentTime, Integer lastNode) {

        P.clear();
        U.clear();
        P.addAll(listRequests);

        //Step 1
        Solucao S = new Solucao();
        String log = "";

        int currentK;
        Map<Integer, Double> CRL = new HashMap<>(n), // Cost Rank List
                NRL = new HashMap<>(n), // Number of Passengers Rank List
                DRL = new HashMap<>(n), // Delivery time-window Rank List
                TRL = new HashMap<>(n), // Time-window Rank List
                NRF = new HashMap<>(n);	// Time-window Rank List

        Iterator<Integer> itK = K.iterator();
        U.clear();
        while (!P.isEmpty() && itK.hasNext()) {

            SeparaOrigemDestino(U, Pin, Pout, n, P);

            //Step 2
            Rota R = new Rota();
            currentK = itK.next();
            log += "\tGROTA " + (currentK + 1) + " ";

            //Step 3
            R.addVisitacao(0);
            currentTime = 0;
            double max, min;
            lastNode = R.getLastNode();

            boolean encontrado;

            while (!P.isEmpty()) {
                encontrado = false;
                m.clear();
                for (int i = 0; i < n; i++) {
                    m.add(Pin.get(i).size() - Pout.get(i).size());
                }

                //Step 4
                Set<Integer> FeasibleNode = new HashSet<>();
                List<Integer> EarliestTime = new ArrayList<>();

                EncontraNosViaveis(n, lastNode, encontrado, Qmax, R, Pin, Pout, FeasibleNode, d, currentTime, TimeWindows);

                //System.out.println("FEASIBLE NODES = "+ FeasibleNode);			
                if (FeasibleNode.size() > 1) {
                    //Step 4.1
                    CalculaCRL(FeasibleNode, CRL, c, lastNode);
                    //Step 4.2
                    CalculaNRL(FeasibleNode, NRL, m, lastNode);
                    //Step 4.3
                    CalculaDRL(FeasibleNode, DRL, Pout, lastNode, d, EarliestTime);
                    //Step 4.4
                    CalculaTRL(FeasibleNode, TRL, Pin, lastNode, d, EarliestTime);
                } else {
                    //Step 4.1
                    CalculaListaSemNosViaveis(CRL, FeasibleNode);
                    //Step 4.2
                    CalculaListaSemNosViaveis(NRL, FeasibleNode);
                    //Step 4.3
                    CalculaListaSemNosViaveis(DRL, FeasibleNode);
                    //Step 4.4
                    CalculaListaSemNosViaveis(TRL, FeasibleNode);
                }

                //Step 5 
                //CalculaNRF(NRF,CRL,NRL,DRL,TRL,alphaD,alphaP,alphaV,alphaT,FeasibleNode);
                Random gerador = new Random(19700621);
                for (Integer i : FeasibleNode) {
                    NRF.put(i, gerador.nextDouble());
                }
                //Step 6              
                //System.out.println("Tamanho da NRF = " + NRF.size());              
                max = Collections.max(NRF.values());

                currentTime = AdicionaNo(NRF, CRL, NRL, DRL, TRL, max, lastNode, Pin, d, EarliestTime, currentTime, R);
                lastNode = R.getLastNode();

                //Step 7
                //RETIRAR A LINHA DE BAIXO DEPOIS - inicialização de listRequestAux
                List<Request> listRequestAux = new LinkedList<>();
                //Desembarca as solicitações no nó 
                Desembarca(Pin, Pout, lastNode, currentTime, P, listRequestAux, R, log);
                //Embarca as solicitações sem tempo de espera
                Embarca(Pin, lastNode, currentTime, P, listRequestAux, R, log, Qmax);
                //Embarca agora as solicitações onde o veículo precisar esperar e guarda atualiza o tempo (currentTime)                               
                currentTime = EmbarcaRelaxacao(Pin, lastNode, currentTime, P, listRequestAux, R, log, Qmax, TimeWindows);

                //---------- Trata as solicitações inviáveis -----------
                RetiraSolicitacoesInviaveis(Pin, Pout, listRequestAux, currentTime, P, U);
                encontrado = ProcuraSolicitacaoParaAtender(R, Qmax, Pin, Pout, currentTime, n, d, lastNode, TimeWindows, encontrado);
                RetiraSolicitacaoNaoSeraAtendida(encontrado, Pin, Pout, listRequestAux, currentTime, P, U);

                //Step 8
                currentTime = FinalizaRota(P, R, currentTime, lastNode, d, S);
            }

            //Step 9
            AnaliseSolicitacoesViaveisEmU(U, P, itK, d);
        }

//        S.setListaNaoAtendimento(U);
//        //S.setfObjetivo1(FOp(S, c));
//        S.setLogger(log);
//        S.concatenarRota();
        //S.setFuncaoObjetivo(FuncaoObjetivo(S,c));
        S.setListaNaoAtendimento(U);
        S.setfObjetivo1(FO1(S, c));
        S.setfObjetivo2(FO2(S, c));
        S.setfObjetivo3(FO3(S));
        S.setfObjetivo4(FO4(S));
        S.setfObjetivo5(FO5(S));
        S.setfObjetivo6(FO6(S));
        S.setfObjetivo7(FO7(S));
        S.setfObjetivo8(FO8(S));
        S.setfObjetivo9(FO9(S));
        FOagregados(S, 1, 1, 1, 1, 1);
        S.setLogger(log);
        S.concatenarRota();
        //S.setfObjetivo1((int) FuncaoObjetivo(S, c));
        S.setFuncaoObjetivo(FuncaoDeAvaliacao(S, listRequests, c));

        return S;
    }

    public static void Mutacao(List<Solucao> Pop, double Pm, List<Request> listRequests, Map<Integer, List<Request>> Pin,
            Map<Integer, List<Request>> Pout, Integer n, Integer Qmax, Set<Integer> K, List<Request> U, List<Request> P,
            List<Integer> m, List<List<Integer>> d, List<List<Integer>> c, Integer TimeWindows, Integer currentTime, Integer lastNode) {
        Random rnd = new Random();
        Random p1 = new Random();
        Random p2 = new Random();
        int posicao1, posicao2;
        double prob;

        for (int i = 0; i < Pop.size(); i++) {
            prob = rnd.nextFloat();

            if (prob < Pm) {
                List<Integer> individuo = new ArrayList<>(Pop.get(i).getRotaConcatenada());
                posicao1 = p1.nextInt(individuo.size());

                do {
                    posicao2 = p2.nextInt(individuo.size());
                } while (Objects.equals(individuo.get(posicao1), individuo.get(posicao2)));

                Collections.swap(individuo, posicao1, posicao2);

                Solucao S = new Solucao();
                S.setSolucao(avaliaSolucao(individuo, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
                Pop.get(i).setSolucao(S);
            }
        }
    }

    public static void MutacaoShuffle(List<Solucao> Pop, double Pm, List<Request> listRequests, Map<Integer, List<Request>> Pin,
            Map<Integer, List<Request>> Pout, Integer n, Integer Qmax, Set<Integer> K, List<Request> U, List<Request> P,
            List<Integer> m, List<List<Integer>> d, List<List<Integer>> c, Integer TimeWindows, Integer currentTime, Integer lastNode) {
        Random rnd = new Random();
        Random p1 = new Random();
        Random p2 = new Random();
        int posicao1, posicao2;
        double prob;

        for (int i = 0; i < Pop.size(); i++) {
            prob = rnd.nextFloat();

            if (prob < Pm) {
                List<Integer> individuo = new ArrayList<>(Pop.get(i).getRotaConcatenada());
                Collections.shuffle(individuo);
                Solucao S = new Solucao();
                S.setSolucao(avaliaSolucao(individuo, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
                Pop.get(i).setSolucao(S);
            }
        }
    }

    public static void Mutacao2Opt(List<Solucao> Pop, double Pm, List<Request> listRequests, Map<Integer, List<Request>> Pin,
            Map<Integer, List<Request>> Pout, Integer n, Integer Qmax, Set<Integer> K, List<Request> U, List<Request> P,
            List<Integer> m, List<List<Integer>> d, List<List<Integer>> c, Integer TimeWindows, Integer currentTime, Integer lastNode) {
        Random rnd = new Random();
        Random p1 = new Random();
        Random p2 = new Random();
        int posicao1, posicao2;
        double prob;

        for (int i = 0; i < Pop.size(); i++) {
            prob = rnd.nextFloat();

            if (prob < Pm) {
                List<Integer> individuo = new ArrayList<>(Pop.get(i).getRotaConcatenada());

                int index1, index2;

                do {
                    index1 = rnd.nextInt(individuo.size());
                    index2 = rnd.nextInt(individuo.size());
                } while (index1 == index2);

                List<Integer> indices = new ArrayList<>();
                indices.add(index1);
                indices.add(index2);

                int min = Collections.min(indices);
                int max = Collections.max(indices);

                List<Integer> aux = new ArrayList<>(individuo.subList(min, max));

                Collections.reverse(aux);

                individuo.subList(min, max).clear();
                individuo.addAll(min, aux);

                Solucao S = new Solucao();
                S.setSolucao(avaliaSolucao(individuo, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
                Pop.get(i).setSolucao(S);
            }
        }
    }

    public static void Mutacao2Shuffle(List<Solucao> Pop, double Pm, List<Request> listRequests, Map<Integer, List<Request>> Pin,
            Map<Integer, List<Request>> Pout, Integer n, Integer Qmax, Set<Integer> K, List<Request> U, List<Request> P,
            List<Integer> m, List<List<Integer>> d, List<List<Integer>> c, Integer TimeWindows, Integer currentTime, Integer lastNode) {
        Random rnd = new Random();
        Random p1 = new Random();
        Random p2 = new Random();
        int posicao1, posicao2;
        double prob;

        for (int i = 0; i < Pop.size(); i++) {
            prob = rnd.nextFloat();

            if (prob < Pm) {
                List<Integer> individuo = new ArrayList<>(Pop.get(i).getRotaConcatenada());

                int index1, index2;

                do {
                    index1 = rnd.nextInt(individuo.size());
                    index2 = rnd.nextInt(individuo.size());
                } while (index1 == index2);

                List<Integer> indices = new ArrayList<>();
                indices.add(index1);
                indices.add(index2);

                int min = Collections.min(indices);
                int max = Collections.max(indices);

                List<Integer> aux = new ArrayList<>(individuo.subList(min, max));

                Collections.shuffle(aux);

                individuo.subList(min, max).clear();
                individuo.addAll(min, aux);

                Solucao S = new Solucao();
                S.setSolucao(avaliaSolucao(individuo, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
                Pop.get(i).setSolucao(S);
            }
        }
    }

    public static void MutacaoILS(List<Solucao> Pop, double Pm, List<Request> listRequests, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout,
            Integer n, Integer Qmax, Set<Integer> K, List<Request> U, List<Request> P, List<Integer> m, List<List<Integer>> d, List<List<Integer>> c,
            Integer TimeWindows, Integer currentTime, Integer lastNode) {
        Random rnd = new Random();
        Random p1 = new Random();
        Random p2 = new Random();
        int posicao1, posicao2;
        double prob;

        for (int i = 0; i < Pop.size(); i++) {
            prob = rnd.nextFloat();
            //System.out.println("Prob gerada = " + prob);
            if (prob < Pm) {

                Solucao S = new Solucao();
                //S.setSolucao(ILS(Pop.get(i), listRequests, Pin, Pout, n, Qmax, K, U, P, m, d, c, TimeWindows));
                //S.setSolucao(VND(Pop.get(i), listRequests,  P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
                S.setSolucao(primeiroMelhorVizinho(Pop.get(i), 2, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
                Pop.get(i).setSolucao(S);
            }
        }
    }

    public static void Fitness(List<Solucao> Pop) {
        int soma = 0;
        for (int i = 0; i < Pop.size(); i++) {
            soma += Pop.get(i).getFuncaoObjetivo();
        }
        List<Double> fit = new ArrayList<>();

        for (int i = 0; i < Pop.size(); i++) {
            fit.add((double) Pop.get(i).getFuncaoObjetivo() / soma);
        }
        Collections.sort(fit);
        Collections.reverse(fit);

        for (int i = 0; i < Pop.size(); i++) {
            Pop.get(i).setFitness(fit.get(i));
        }
    }

    public static void NewFitness(List<Solucao> population) {
        double max = population.stream().mapToDouble(Solucao::getFuncaoObjetivo).max().getAsDouble();
        double min = population.stream().mapToDouble(Solucao::getFuncaoObjetivo).min().getAsDouble();
        population.forEach(u -> u.setFitness((max - u.getFuncaoObjetivo()) / (max - min)));
                
        double sum = population.stream().mapToDouble(Solucao::getFitness).sum();
        population.forEach(u -> u.setFitness(u.getFitness() / sum));
    }

    public static void OrdenaPopulacao(List<Solucao> Pop) {
        Collections.sort(Pop);
    }

    public static void ImprimePopulacao(List<Solucao> Pop) {
        for (int i = 0; i < Pop.size(); i++) {
            //System.out.println("Pop(" + i + ") = " + Pop.get(i));
            System.out.println(Pop.get(i));
            //System.out.println(Pop.get(i).getRotaConcatenada());
        }
    }

    public static void Selecao(List<Integer> pais, List<Solucao> Pop, Integer TamMax) {
        Random rnd = new Random();
        double valor;
        double soma;
        int pos;
        pais.clear();
        for (int i = 0; i < TamMax; i++) {
            soma = 0;
            pos = -1;
            valor = rnd.nextFloat()/10;
            //System.out.println(valor);
            //ImprimePopulacao(Pop);
            for (int j = 0; j < Pop.size(); j++) {
                soma += Pop.get(j).getFitness();
                if (valor <= soma) {
                    pos = j;
                    pais.add(pos);
                    break;
                }
            }
            if (pos == -1) {
                pos = rnd.nextInt(Pop.size());
                pais.add(pos);
                //System.out.println("Precisou");
            }
        }
    }

    public static void Cruzamento(List<Solucao> Pop_nova, List<Solucao> Pop, Integer TamMax, double Pc, List<Integer> pais, List<Request> listRequests,
            List<Request> P, Set<Integer> K, List<Request> U, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout, List<List<Integer>> d, List<List<Integer>> c, Integer n, Integer Qmax, Integer TimeWindows) {
        int pai;
        int mae;
        int pontoCorte;
        int menorTamanho;
        double valor;
        Random rnd = new Random();
        List<Solucao> NewPop = new ArrayList<>();
        List<Integer> filho1 = new ArrayList<>();
        List<Integer> filho2 = new ArrayList<>();
        NewPop.clear();
        for (int i = 0; i < (pais.size() - 1); i = i + 2) {

            valor = rnd.nextFloat();

            pai = pais.get(i);
            mae = pais.get(i + 1);
            Solucao s1 = new Solucao();
            Solucao s2 = new Solucao();
            if (valor < Pc) {

                menorTamanho = Math.min(Pop.get(pai).getRotaConcatenada().size(), Pop.get(mae).getRotaConcatenada().size());

                pontoCorte = rnd.nextInt(menorTamanho);

                filho1.addAll(Pop.get(pai).getRotaConcatenada().subList(0, pontoCorte));
                filho1.addAll((Pop.get(mae).getRotaConcatenada().subList(pontoCorte, Pop.get(mae).getRotaConcatenada().size())));
                filho2.addAll(Pop.get(mae).getRotaConcatenada().subList(0, pontoCorte));
                filho2.addAll((Pop.get(pai).getRotaConcatenada().subList(pontoCorte, Pop.get(pai).getRotaConcatenada().size())));

                s1.setSolucao(avaliaSolucao(filho1, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
                s2.setSolucao(avaliaSolucao(filho2, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

            } else {
                s1.setSolucao(Pop.get(mae));
                s2.setSolucao(Pop.get(pai));
            }

            NewPop.add(s1);
            NewPop.add(s2);
            //s1.resetSolucao(-1);
            //System.out.println("Pai = "+ pai +" Mae = " + mae);
            filho1.clear();
            filho2.clear();
        }
        //Pop.clear();
        //Pop.addAll(NewPop);
        //NewPop.clear();
        //OrdenaPopulacao(Pop);
        //Fitness(Pop);
        Pop_nova.clear();
        Pop_nova.addAll(NewPop);
        NewPop.clear();
        OrdenaPopulacao(Pop_nova);
    }

    public static void Cruzamento2Pontos(List<Solucao> Pop_nova, List<Solucao> Pop, Integer TamMax, double Pc, List<Integer> pais, List<Request> listRequests,
            List<Request> P, Set<Integer> K, List<Request> U, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout,
            List<List<Integer>> d, List<List<Integer>> c, Integer n, Integer Qmax, Integer TimeWindows) {
        int pai;
        int mae;
        int pontoCorte;
        int menorTamanho;
        double valor;
        Random rnd = new Random();
        List<Solucao> NewPop = new ArrayList<>();
        List<Integer> filho1 = new ArrayList<>();
        List<Integer> filho2 = new ArrayList<>();
        NewPop.clear();
        try {
            for (int i = 0; i < (TamMax - 1); i = i + 2) {

                valor = rnd.nextFloat();

                pai = pais.get(i);
                mae = pais.get(i + 1);
                Solucao s1 = new Solucao();
                Solucao s2 = new Solucao();
                if (valor < Pc) {

                    int index1, index2;
                    menorTamanho = Math.min(Pop.get(pai).getRotaConcatenada().size(), Pop.get(mae).getRotaConcatenada().size());

                    filho1.addAll(Pop.get(pai).getRotaConcatenada());
                    filho2.addAll(Pop.get(mae).getRotaConcatenada());

                    do {
                        index1 = rnd.nextInt(menorTamanho);
                        index2 = rnd.nextInt(menorTamanho);
                    } while (index1 == index2);

                    List<Integer> indices = new ArrayList<>();
                    indices.add(index1);
                    indices.add(index2);

                    int min = Collections.min(indices);
                    int max = Collections.max(indices);
                    //System.out.println("Indices = " + indices);
                    //System.out.println("Min = " + min + " Max = " + max);

                    List<Integer> parte1 = new ArrayList<>(filho1.subList(min, max));
                    List<Integer> parte2 = new ArrayList<>(filho2.subList(min, max));

                    filho1.subList(min, max).clear();
                    filho2.subList(min, max).clear();

                    //System.out.println(filho1);
                    //System.out.println(filho2);
                    //System.out.println("Cruzou!!!");
                    filho1.addAll(min, parte2);
                    filho2.addAll(min, parte1);

                    s1.setSolucao(avaliaSolucao(filho1, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
                    s2.setSolucao(avaliaSolucao(filho2, listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                } else {
                    s1.setSolucao(Pop.get(mae));
                    s2.setSolucao(Pop.get(pai));
                }

                NewPop.add(s1);
                NewPop.add(s2);
                //s1.resetSolucao(-1);
                //System.out.println("Pai = "+ pai +" Mae = " + mae);
                filho1.clear();
                filho2.clear();
            }
            //Pop.clear();
            Pop_nova.clear();
            Pop_nova.addAll(NewPop);
            NewPop.clear();
            //OrdenaPopulacao(Pop_nova);
            //Fitness(Pop);
            //FitnessSPEA2(Pop_nova);
        } catch (IllegalArgumentException e) {
            ImprimePopulacao(Pop);
        }
    }

    public static Solucao CopiaMelhorSolucao(List<Solucao> population, Solucao SBest) {
//        for (Solucao S : population) {
//            if (S.compareTo(SBest) == -1) {
//                SBest.setSolucao(S);
//                System.out.println(SBest);
//                return SBest;
//            }
//        }
//        return SBest;
//        if(population.get(0).getFuncaoObjetivo() < SBest.getFuncaoObjetivo()){
//            SBest.setSolucao(population.get(0));
//        }
//        //SBest.setSolucao(population.stream().collect(Collectors.maxBy(Solucao::getFitness)));
//        return SBest;
        for (Solucao S : population) {
            if(S.getFuncaoObjetivo() < SBest.getFuncaoObjetivo()){
                SBest.setSolucao(S);
            }
        }
        return SBest;
    }

    public static void InsereMelhorIndividuo(List<Solucao> Pop, Solucao SBest) {
        Pop.get(Pop.size() - 1).setSolucao(SBest);
    }

    public static void SalvaPopulacao(List<Solucao> Pop, Integer Geracao) {
        String diretorio, nomeArquivo;
        try {
            diretorio = "\\home\\renan";
            nomeArquivo = "Solucao";
            boolean success = (new File(diretorio)).mkdirs();
            if (!success) {
                System.out.println("Diretórios ja existem!");
            }
            PrintStream saida;
            saida = new PrintStream(diretorio + "\\GA-MESTRADO" + nomeArquivo + ".txt");

            saida.print("\tGeração = " + Geracao + "\n");
            for (Solucao S : Pop) {
                saida.print("\t" + S + "\n");
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Solucao primeiroMelhorVizinho(Solucao s, int tipoMovimento, List<Request> listRequests, List<Request> P, Set<Integer> K,
            List<Request> U, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout,
            List<List<Integer>> d, List<List<Integer>> c, Integer n, Integer Qmax, Integer TimeWindows) {
        Solucao melhor = new Solucao(s);

        Solucao aux = new Solucao();

        List<Integer> original = new ArrayList<Integer>(s.getRotaConcatenada());

        List<Integer> vizinho = new ArrayList<Integer>();

        /**
         * Tipo Estrategia: 1 - melhorVizinho, 2 - primeiroMelhorVizinho Tipo
         * Movimento: 1 - Troca, 2 - Substituicao, 3 - Deslocamento, 4 -
         * Aleatoria
         *
         */
        switch (tipoMovimento) {
            case 1: // troca						

                for (int i = 0; i < original.size() - 1; i++) {
                    for (int j = i + 1; j < original.size(); j++) {
                        vizinho.addAll(original);

                        if (vizinho.get(i) != vizinho.get(j)) {
                            Collections.swap(vizinho, i, j);

                            aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                            if (aux.getfObjetivo1() < melhor.getfObjetivo1()) {
                                //System.out.println("ACHEI TROCA-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                                melhor.setSolucao(aux);
                                return melhor;
                            }
                        }
                        vizinho.clear();
                    }
                }
                break;

            case 2: // substituicao

                for (int i = 0; i < original.size(); i++) {
                    for (int j = 1; j < n; j++) {
                        vizinho.addAll(original);

                        if (vizinho.get(i) != j) {
                            vizinho.set(i, j);

                            aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                            if (aux.getfObjetivo1() < melhor.getfObjetivo1()) {
                                //System.out.println("ACHEI INSERCAO-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                                melhor.setSolucao(aux);
                                return melhor;
                            }
                        }

                        vizinho.clear();
                    }
                }
                break;

            case 3: // deslocamento

                for (int i = 0; i < original.size(); i++) {
                    for (int j = 0; j < original.size(); j++) {
                        if (i != j) {
                            vizinho.addAll(original);
                            vizinho.remove(i);
                            vizinho.add(j, original.get(i));

                            aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                            if (aux.getfObjetivo1() < melhor.getfObjetivo1()) {
                                //System.out.println("ACHEI MOVIMENTO-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                                melhor.setSolucao(aux);
                                return melhor;
                            }
                        }
                        vizinho.clear();
                    }
                }

                break;

            case 4: // aleatoria

                Random r1 = new Random();

                int escolhaVizinho;

                Random r2 = new Random(System.nanoTime());

                int qtd = (int) (0.1 * (original.size() * original.size()));

                int elemento,
                 posicao,
                 posicao1,
                 posicao2;

                for (int i = 0; i < qtd; i++) {//???QUANTAS VEZES S�O NECESS�RIAS...

                    vizinho.addAll(original);

                    escolhaVizinho = r1.nextInt(120);

                    if (escolhaVizinho < 20 || escolhaVizinho >= 60 && escolhaVizinho < 80) {
                        //Troca

                        posicao1 = r1.nextInt(original.size());

                        do {
                            posicao2 = r2.nextInt(original.size());
                        } while (vizinho.get(posicao1) == vizinho.get(posicao2));

                        Collections.swap(vizinho, posicao1, posicao2);

                    } else if (escolhaVizinho >= 20 && escolhaVizinho < 40 || escolhaVizinho >= 80 && escolhaVizinho < 100) {
                        //Insercao

                        posicao = r1.nextInt(original.size());

                        do {
                            elemento = r2.nextInt(n);
                        } while (elemento == 0 || elemento == vizinho.get(posicao));

                        vizinho.set(posicao, elemento);
                    } else {
                        //Movimento

                        posicao1 = r1.nextInt(original.size());

                        do {
                            posicao2 = r2.nextInt(original.size());
                        } while (posicao1 == posicao2);

                        vizinho.remove(posicao1);
                        vizinho.add(posicao2, original.get(posicao1));
                    }

                    aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                    if (aux.getfObjetivo1() < melhor.getfObjetivo1()) {
                        //System.out.println("ACHEI ALEATORIA-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                        melhor.setSolucao(aux);
                        return melhor;

                    }

                    vizinho.clear();
                }

                break;

            case 5:
                for (int i = 0; i < original.size() - 1; i++) {
                    int contador = 1;
                    for (int j = 0; j < original.size(); j++) {
                        if ((i != j) && (j != i + 1)) {

                            vizinho.addAll(original);
                            List<Integer> nosRetirados = new ArrayList<>(vizinho.subList(i, i + 2));

                            vizinho.subList(i, i + 2).clear();
                            vizinho.addAll(contador, nosRetirados);
                            contador++;

                            aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                            if (aux.getfObjetivo1() < melhor.getfObjetivo1()) {
                                //System.out.println("ACHEI MOVIMENTO-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                                melhor.setSolucao(aux);
                                return melhor;

                            }
                        }
                        vizinho.clear();
                    }
                }

                break;
            case 6:
                for (int i = 0; i < original.size() - 2; i++) {
                    int contador = 1;
                    for (int j = 0; j < original.size(); j++) {
                        if ((i != j) && (j != i + 1) && (j != i + 2)) {

                            vizinho.addAll(original);
                            List<Integer> nosRetirados = new ArrayList<>(vizinho.subList(i, i + 3));

                            vizinho.subList(i, i + 3).clear();
                            vizinho.addAll(contador, nosRetirados);
                            contador++;

                            aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                            if (aux.getfObjetivo1() < melhor.getfObjetivo1()) {
                                //System.out.println("ACHEI MOVIMENTO-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                                melhor.setSolucao(aux);
                                return melhor;

                            }
                        }
                        vizinho.clear();
                    }
                }
                break;
        }

        return melhor;
    }

    public static Solucao melhorVizinho(Solucao s, int tipoMovimento, List<Request> listRequests, List<Request> P, Set<Integer> K,
            List<Request> U, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout,
            List<List<Integer>> d, List<List<Integer>> c, Integer n, Integer Qmax, Integer TimeWindows) {
        Solucao melhor = new Solucao(s);

        Solucao aux = new Solucao();

        List<Integer> original = new ArrayList<Integer>(s.getRotaConcatenada());

        List<Integer> vizinho = new ArrayList<Integer>();

        /**
         * Tipo Estrategia: 1 - melhorVizinho, 2 - primeiroMelhorVizinho Tipo
         * Movimento: 1 - Troca, 2 - Substituicao, 3 - Deslocamento, 4 -
         * Aleatoria
         *
         */
        switch (tipoMovimento) {
            case 1: // troca						

                for (int i = 0; i < original.size() - 1; i++) {
                    for (int j = i + 1; j < original.size(); j++) {
                        vizinho.addAll(original);

                        if (vizinho.get(i) != vizinho.get(j)) {
                            Collections.swap(vizinho, i, j);

                            aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                            if (aux.getFuncaoObjetivo() < melhor.getFuncaoObjetivo()) {
                                //System.out.println("ACHEI TROCA-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                                melhor.setSolucao(aux);

                            }
                        }
                        vizinho.clear();
                    }
                }
                break;

            case 2: // substituicao

                for (int i = 0; i < original.size(); i++) {
                    for (int j = 1; j < n; j++) {
                        vizinho.addAll(original);

                        if (vizinho.get(i) != j) {
                            vizinho.set(i, j);

                            aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                            if (aux.getFuncaoObjetivo() < melhor.getFuncaoObjetivo()) {
                                //System.out.println("ACHEI INSERCAO-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                                melhor.setSolucao(aux);

                            }
                        }

                        vizinho.clear();
                    }
                }
                break;

            case 3: // deslocamento

                for (int i = 0; i < original.size(); i++) {
                    for (int j = 0; j < original.size(); j++) {
                        if (i != j) {
                            vizinho.addAll(original);
                            vizinho.remove(i);
                            vizinho.add(j, original.get(i));

                            aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                            if (aux.getFuncaoObjetivo() < melhor.getFuncaoObjetivo()) {
                                //System.out.println("ACHEI MOVIMENTO-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                                melhor.setSolucao(aux);

                            }
                        }
                        vizinho.clear();
                    }
                }

                break;

            case 4: // aleatoria

                Random r1 = new Random();

                int escolhaVizinho;

                Random r2 = new Random(System.nanoTime());

                int qtd = (int) (0.1 * (original.size() * original.size()));

                int elemento,
                 posicao,
                 posicao1,
                 posicao2;

                for (int i = 0; i < qtd; i++) {//???QUANTAS VEZES S�O NECESS�RIAS...

                    vizinho.addAll(original);

                    escolhaVizinho = r1.nextInt(120);

                    if (escolhaVizinho < 20 || escolhaVizinho >= 60 && escolhaVizinho < 80) {
                        //Troca

                        posicao1 = r1.nextInt(original.size());

                        do {
                            posicao2 = r2.nextInt(original.size());
                        } while (vizinho.get(posicao1) == vizinho.get(posicao2));

                        Collections.swap(vizinho, posicao1, posicao2);

                    } else if (escolhaVizinho >= 20 && escolhaVizinho < 40 || escolhaVizinho >= 80 && escolhaVizinho < 100) {
                        //Insercao

                        posicao = r1.nextInt(original.size());

                        do {
                            elemento = r2.nextInt(n);
                        } while (elemento == 0 || elemento == vizinho.get(posicao));

                        vizinho.set(posicao, elemento);
                    } else {
                        //Movimento

                        posicao1 = r1.nextInt(original.size());

                        do {
                            posicao2 = r2.nextInt(original.size());
                        } while (posicao1 == posicao2);

                        vizinho.remove(posicao1);
                        vizinho.add(posicao2, original.get(posicao1));
                    }

                    aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                    if (aux.getFuncaoObjetivo() < melhor.getFuncaoObjetivo()) {
                        //System.out.println("ACHEI ALEATORIA-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                        melhor.setSolucao(aux);

                    }

                    vizinho.clear();
                }

                break;

            case 5:
                for (int i = 0; i < original.size() - 1; i++) {
                    int contador = 1;
                    for (int j = 0; j < original.size(); j++) {
                        if ((i != j) && (j != i + 1)) {

                            vizinho.addAll(original);
                            List<Integer> nosRetirados = new ArrayList<>(vizinho.subList(i, i + 2));

                            vizinho.subList(i, i + 2).clear();
                            vizinho.addAll(contador, nosRetirados);
                            contador++;

                            aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                            if (aux.getFuncaoObjetivo() < melhor.getFuncaoObjetivo()) {
                                //System.out.println("ACHEI MOVIMENTO-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                                melhor.setSolucao(aux);

                            }
                        }
                        vizinho.clear();
                    }
                }

                break;
            case 6:
                for (int i = 0; i < original.size() - 2; i++) {
                    int contador = 1;
                    for (int j = 0; j < original.size(); j++) {
                        if ((i != j) && (j != i + 1) && (j != i + 2)) {

                            vizinho.addAll(original);
                            List<Integer> nosRetirados = new ArrayList<>(vizinho.subList(i, i + 3));

                            vizinho.subList(i, i + 3).clear();
                            vizinho.addAll(contador, nosRetirados);
                            contador++;

                            aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                            if (aux.getFuncaoObjetivo() < melhor.getFuncaoObjetivo()) {
                                //System.out.println("ACHEI MOVIMENTO-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                                melhor.setSolucao(aux);

                            }
                        }
                        vizinho.clear();
                    }
                }
                break;
        }

        return melhor;
    }

    public static Solucao primeiroMelhorVizinhoAleatorio(Solucao s, int tipoMovimento, List<Request> listRequests, List<Request> P, Set<Integer> K,
            List<Request> U, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout,
            List<List<Integer>> d, List<List<Integer>> c, Integer n, Integer Qmax, Integer TimeWindows) {
        Solucao melhor = new Solucao(s);
        Solucao aux = new Solucao();
        List<Integer> original = new ArrayList<>(s.getRotaConcatenada());
        List<Integer> vizinho = new ArrayList<>();
        int qtd = (int) (0.1 * (original.size() * original.size()));
        qtd = 100;
        Random r1 = new Random();
        //System.out.println("original.size() = " + original.size() );
        //System.out.println("s.getRotaConcatenada().size() = " + s.getRotaConcatenada().size());
        int escolhaVizinho;
        int elemento;
        Random r2 = new Random(System.nanoTime());

        int posicao, posicao1, posicao2, contador;
        /**
         * Tipo Estrategia: 1 - melhorVizinho, 2 - primeiroMelhorVizinho Tipo
         * Movimento: 1 - Troca, 2 - Substituicao, 3 - Deslocamento, 4 -
         * Aleatoria
         *
         */
        //System.out.println("qtd = " + qtd);

        switch (tipoMovimento) {
            case 1: // troca						

                contador = 0;
                for (int i = 0; i < qtd; i++) {//???QUANTAS VEZES S�O NECESS�RIAS...
                    //System.out.println("Contador = " + contador);
                    contador++;
                    vizinho.addAll(original);

                    posicao1 = r1.nextInt(original.size());

                    do {
                        posicao2 = r2.nextInt(original.size());
                    } while (Objects.equals(vizinho.get(posicao1), vizinho.get(posicao2)));

                    Collections.swap(vizinho, posicao1, posicao2);
                    aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
                    //System.out.println("Posições da troca = " + posicao1 + "\t" + posicao2);
                    //System.out.println("Solucao gerada = " + aux);
                    if (aux.getfObjetivo1() < melhor.getfObjetivo1()) {
                        melhor.setSolucao(aux);
                        return melhor;
                    }
                }
                vizinho.clear();
                break;

            case 2: //substituicao
                contador = 0;
                for (int i = 0; i < qtd; i++) {
                    //System.out.println("Contador = " + contador);
                    contador++;
                    vizinho.addAll(original);
                    posicao = r1.nextInt(original.size());

                    do {
                        elemento = r2.nextInt(n);
                    } while (elemento == 0 || elemento == vizinho.get(posicao));

                    vizinho.set(posicao, elemento);
                    aux.setSolucao(avaliaSolucao(new ArrayList<>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
                    //System.out.println("Posições da troca = " + posicao + "\t" + elemento);
                    //System.out.println("Solucao gerada = " + aux);
                    if (aux.getfObjetivo1() < melhor.getfObjetivo1()) {
                        melhor.setSolucao(aux);
                        return melhor;
                    }
                }
                vizinho.clear();
                break;

            case 3: // deslocamento
                contador = 0;
                for (int i = 0; i < qtd; i++) {
                    //System.out.println("Contador = " + contador);
                    contador++;
                    vizinho.addAll(original);
                    posicao1 = r1.nextInt(original.size());

                    do {
                        posicao2 = r2.nextInt(original.size());
                    } while (posicao1 == posicao2);

                    vizinho.remove(posicao1);
                    vizinho.add(posicao2, original.get(posicao1));

                    aux.setSolucao(avaliaSolucao(new ArrayList<>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
                    if (aux.getfObjetivo1() < melhor.getfObjetivo1()) {
                        //System.out.println("ACHEI MOVIMENTO-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
                        melhor.setSolucao(aux);
                        return melhor;
                    }
                }
                vizinho.clear();
                break;
        }
        return melhor;
    }

    public static Solucao vizinhoAleatorio(Solucao s, int semente1, int semente2, int tipoMovimento, List<Request> listRequests, List<Request> P, Set<Integer> K,
            List<Request> U, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout,
            List<List<Integer>> d, List<List<Integer>> c, Integer n, Integer Qmax, Integer TimeWindows) {
        Solucao melhor = new Solucao(s);
        Solucao aux = new Solucao();
        List<Integer> original = new ArrayList<>(s.getRotaConcatenada());
        List<Integer> vizinho = new ArrayList<>();
        int qtd = (int) (0.1 * (original.size() * original.size()));
        qtd = 100;
        Random r1 = new Random(semente1);
        //System.out.println("original.size() = " + original.size() );
        //System.out.println("s.getRotaConcatenada().size() = " + s.getRotaConcatenada().size());
        int escolhaVizinho;
        int elemento;
        Random r2 = new Random(semente2);

        int posicao, posicao1, posicao2, contador;
        /**
         * Tipo Estrategia: 1 - melhorVizinho, 2 - primeiroMelhorVizinho Tipo
         * Movimento: 1 - Troca, 2 - Substituicao, 3 - Deslocamento, 4 -
         * Aleatoria
         *
         */
        //System.out.println("qtd = " + qtd);

        switch (tipoMovimento) {
            case 1: // troca						

                contador = 0;
                for (int i = 0; i < qtd; i++) {//???QUANTAS VEZES S�O NECESS�RIAS...
                    //System.out.println("Contador = " + contador);
                    contador++;
                    vizinho.addAll(original);

                    posicao1 = r1.nextInt(original.size());

                    do {
                        posicao2 = r2.nextInt(original.size());
                    } while (Objects.equals(vizinho.get(posicao1), vizinho.get(posicao2)));

                    Collections.swap(vizinho, posicao1, posicao2);
                    aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
                    return aux;
                    //System.out.println("Posições da troca = " + posicao1 + "\t" + posicao2);
                    //System.out.println("Solucao gerada = " + aux);
//                    if (aux.getfObjetivo() < melhor.getfObjetivo()) {
//                        melhor.setSolucao(aux);
//                        return melhor;
//                    }
                }
                vizinho.clear();
                break;

            case 2: //substituicao
                contador = 0;
                for (int i = 0; i < qtd; i++) {
                    //System.out.println("Contador = " + contador);
                    contador++;
                    vizinho.addAll(original);
                    posicao = r1.nextInt(original.size());

                    do {
                        elemento = r2.nextInt(n);
                    } while (elemento == 0 || elemento == vizinho.get(posicao));

                    vizinho.set(posicao, elemento);
                    aux.setSolucao(avaliaSolucao(new ArrayList<>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
                    //System.out.println("Posições da troca = " + posicao + "\t" + elemento);
                    //System.out.println("Solucao gerada = " + aux);
                    return aux;
//                    if (aux.getfObjetivo() < melhor.getfObjetivo()) {
//                        melhor.setSolucao(aux);
//                        return melhor;
//                    }
                }
                vizinho.clear();
                break;

            case 3: // deslocamento
                contador = 0;
                for (int i = 0; i < qtd; i++) {
                    //System.out.println("Contador = " + contador);
                    contador++;
                    vizinho.addAll(original);
                    posicao1 = r1.nextInt(original.size());

                    do {
                        posicao2 = r2.nextInt(original.size());
                    } while (posicao1 == posicao2);

                    vizinho.remove(posicao1);
                    vizinho.add(posicao2, original.get(posicao1));

                    aux.setSolucao(avaliaSolucao(new ArrayList<>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
                    return aux;
//                    if (aux.getfObjetivo() < melhor.getfObjetivo()) {
//                        //System.out.println("ACHEI MOVIMENTO-> "+aux.getfObjetivo()+" "+ aux.getListaNaoAtendimento().size());
//                        melhor.setSolucao(aux);
//                        return melhor;
//                    }
                }
                vizinho.clear();
                break;
        }
        return melhor;
    }

    public static Solucao buscaTabu(Solucao inicial, int tipoEstrategia, int tipoMovimento, List<Request> listRequests, List<Request> P, Set<Integer> K,
            List<Request> U, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout, List<List<Integer>> d,
            List<List<Integer>> c, Integer n, Integer Qmax, Integer TimeWindows) {
        Solucao estrela = new Solucao();
        Solucao s = new Solucao(inicial);
        estrela.setSolucao(s);

        int iteracao = 0, //contador do n�mero de itera��es
                melhorIteracao = 0, //itera��o mais recente que forneceu s*
                BTMAX = 10;			//numero m�ximo de itera��es sem melhora em s*
        /**
         *
         * Map<Map<Integer,Integer>,Integer> listaTabu = new
         * HashMap<Map<Integer,Integer>, Integer>();
         *
         *
         *
         * Map<Double,List<Double>> A = new TreeMap<Double, List<Double>>();
         * double doubleAlfa;
         *
         * for(int alfa = 30; alfa <= 70; alfa += 5){
         *
         * doubleAlfa = Double.parseDouble(Float.toString(new
         * Float(alfa*0.01))); A.put(doubleAlfa, new ArrayList<Double>(5));
         *
         * *
         */
        int[][] listaTabuTroca, listaTabuSubstituicao, listaTabuMovimento;

        listaTabuTroca = new int[s.getRotaConcatenada().size()][s.getRotaConcatenada().size()];

        listaTabuSubstituicao = new int[s.getRotaConcatenada().size()][n];

        listaTabuMovimento = new int[s.getRotaConcatenada().size()][s.getRotaConcatenada().size()];

        /**
         * Tipo Estrategia: 1 - melhorVizinho, 2 - primeiroMelhorVizinho Tipo
         * Movimento: 1 - Troca, 2 - Substituicao, 3 - Deslocamento, 4 -
         * Aleatoria
         *
         */
        while (iteracao - melhorIteracao <= BTMAX) {
            iteracao++;
            //System.out.println(iteracao - melhorIteracao);
            s.setSolucao(melhorVizinhoBT(s, estrela, tipoMovimento, listaTabuTroca, listaTabuSubstituicao, listaTabuMovimento, iteracao,
                    listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));
            if (s.getFuncaoObjetivo() < estrela.getFuncaoObjetivo()) {
                estrela.setSolucao(s);
                melhorIteracao = iteracao;
            }

        }

        return estrela;
    }

    public static Solucao melhorVizinhoBT(Solucao s, Solucao estrela, int tipoMovimento, int[][] listaTabuTroca, int[][] listaTabuSubstituicao,
            int[][] listaTabuMovimento, int iteracao, List<Request> listRequests, List<Request> P, Set<Integer> K,
            List<Request> U, Map<Integer, List<Request>> Pin, Map<Integer, List<Request>> Pout, List<List<Integer>> d,
            List<List<Integer>> c, Integer n, Integer Qmax, Integer TimeWindows) {

        Solucao melhor = new Solucao();
        melhor.setfObjetivo1(999999);

        Solucao aux = new Solucao();

        List<Integer> original = new ArrayList<Integer>(s.getRotaConcatenada());

        List<Integer> vizinho = new ArrayList<Integer>();

        /**
         * Tipo Estrategia: 1 - melhorVizinho, 2 - primeiroMelhorVizinho Tipo
         * Movimento: 1 - Troca, 2 - Substituicao, 3 - Deslocamento, 4 -
         * Aleatoria
         *
         */
        // armazena a �ltima troca de posi��es realizada
        int pos1 = -1, pos2 = -1, pos = -1, elem = -1,
                duracaoTabu = 5;
        boolean atualizaListaTabu = false;

        switch (tipoMovimento) {
            case 1: // troca			

                for (int posicao1 = 0; posicao1 < original.size() - 1; posicao1++) {
                    for (int posicao2 = posicao1 + 1; posicao2 < original.size(); posicao2++) {
                        vizinho.addAll(original);

                        if (vizinho.get(posicao1) != vizinho.get(posicao2)) {
                            Collections.swap(vizinho, posicao1, posicao2);

                            aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                            if (aux.getfObjetivo1() < melhor.getfObjetivo1()
                                    && ((listaTabuTroca[posicao2][posicao1] <= iteracao && listaTabuTroca[posicao1][posicao2] <= iteracao)
                                    || aux.getfObjetivo1() < estrela.getfObjetivo1())) {

                                melhor.setSolucao(aux);
                                pos1 = posicao1;
                                pos2 = posicao2;
                                atualizaListaTabu = true;

                            }
                        }
                        vizinho.clear();
                    }
                }

                if (atualizaListaTabu) {
                    listaTabuTroca[pos2][pos1] = iteracao + duracaoTabu;
                    listaTabuTroca[pos1][pos2] = iteracao + duracaoTabu;
                }

                break;

            case 2: // substituicao

                for (int posicao = 0; posicao < original.size(); posicao++) {
                    for (int elemento = 1; elemento < n; elemento++) {
                        vizinho.addAll(original);

                        if (vizinho.get(posicao) != elemento) {
                            vizinho.set(posicao, elemento);

                            aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                            if (aux.getfObjetivo1() < melhor.getfObjetivo1()
                                    && (listaTabuSubstituicao[posicao][elemento] <= iteracao || aux.getfObjetivo1() < estrela.getfObjetivo1())) {

                                melhor.setSolucao(aux);
                                pos = posicao;
                                elem = original.get(posicao);
                                atualizaListaTabu = true;

                            }
                        }

                        vizinho.clear();
                    }
                }

                if (atualizaListaTabu) {
                    listaTabuSubstituicao[pos][elem] = iteracao + duracaoTabu;
                }

                break;

            case 3: // deslocamento

                for (int posicao1 = 0; posicao1 < original.size(); posicao1++) {
                    for (int posicao2 = 0; posicao2 < original.size(); posicao2++) {
                        if (posicao1 != posicao2) {
                            vizinho.addAll(original);
                            vizinho.remove(posicao1);
                            vizinho.add(posicao2, original.get(posicao1));

                            aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                            if (aux.getfObjetivo1() < melhor.getfObjetivo1()
                                    && (listaTabuMovimento[posicao1][posicao2] <= iteracao || aux.getfObjetivo1() < estrela.getfObjetivo1())) {

                                melhor.setSolucao(aux);
                                pos1 = posicao1;
                                pos2 = posicao2;
                                atualizaListaTabu = true;

                            }
                        }
                        vizinho.clear();
                    }
                }

                if (atualizaListaTabu) {
                    listaTabuMovimento[pos2][pos1] = iteracao + duracaoTabu;
                }

                break;

            case 4: // aleatoria

                Random r1 = new Random();

                int escolhaVizinho;

                Random r2 = new Random(System.nanoTime());

                int qtd = (int) (0.1 * (original.size() * original.size()));

                int elemento,
                 posicao,
                 posicao1,
                 posicao2;

                for (int i = 0; i < qtd; i++) {//???QUANTAS VEZES S�O NECESS�RIAS...

                    vizinho.addAll(original);

                    escolhaVizinho = r1.nextInt(120);

                    if (escolhaVizinho < 20 || escolhaVizinho >= 60 && escolhaVizinho < 80) {
                        //Troca

                        posicao1 = r1.nextInt(original.size());

                        do {
                            posicao2 = r2.nextInt(original.size());
                        } while (vizinho.get(posicao1) == vizinho.get(posicao2));

                        Collections.swap(vizinho, posicao1, posicao2);

                        aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                        if (aux.getfObjetivo1() < melhor.getfObjetivo1()
                                && ((listaTabuTroca[posicao2][posicao1] <= iteracao && listaTabuTroca[posicao1][posicao2] <= iteracao)
                                || aux.getfObjetivo1() < estrela.getfObjetivo1())) {

                            melhor.setSolucao(aux);
                            pos1 = posicao1;
                            pos2 = posicao2;

                            listaTabuTroca[pos2][pos1] = iteracao + duracaoTabu;
                            listaTabuTroca[pos1][pos2] = iteracao + duracaoTabu;
                        }
                    } else if (escolhaVizinho >= 20 && escolhaVizinho < 40 || escolhaVizinho >= 80 && escolhaVizinho < 100) {
                        //Substituicao

                        posicao = r1.nextInt(original.size());

                        do {
                            elemento = r2.nextInt(n);
                        } while (elemento == 0 || elemento == vizinho.get(posicao));

                        vizinho.set(posicao, elemento);

                        aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                        if (aux.getfObjetivo1() < melhor.getfObjetivo1()
                                && (listaTabuSubstituicao[posicao][elemento] <= iteracao || aux.getfObjetivo1() < estrela.getfObjetivo1())) {

                            melhor.setSolucao(aux);
                            pos = posicao;
                            elem = original.get(posicao);

                            listaTabuSubstituicao[pos][elem] = iteracao + duracaoTabu;
                        }
                    } else {
                        //Movimento

                        posicao1 = r1.nextInt(original.size());

                        do {
                            posicao2 = r2.nextInt(original.size());
                        } while (posicao1 == posicao2);

                        vizinho.remove(posicao1);
                        vizinho.add(posicao2, original.get(posicao1));

                        aux.setSolucao(avaliaSolucao(new ArrayList<Integer>(vizinho), listRequests, P, K, U, Pin, Pout, d, c, n, Qmax, TimeWindows));

                        if (aux.getfObjetivo1() < melhor.getfObjetivo1()
                                && (listaTabuMovimento[posicao1][posicao2] <= iteracao || aux.getfObjetivo1() < estrela.getfObjetivo1())) {

                            melhor.setSolucao(aux);
                            pos1 = posicao1;
                            pos2 = posicao2;

                            listaTabuMovimento[pos2][pos1] = iteracao + duracaoTabu;
                        }
                    }

                    vizinho.clear();
                }

                break;
        }

        return melhor;

    }
}
