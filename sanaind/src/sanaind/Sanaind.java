/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sanaind;

import java.util.Scanner;
import java.util.*;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author piakumpu
 */
public class Sanaind {

    /**
     * @param args the command line arguments
     */
   // public static Scanner lukija = new Scanner(System.in);
    public static ArrayList<String> lauseet = new ArrayList<String>();
    public static ArrayList<String> sanat = new ArrayList<String>();
    
    public static void lukukone() {
        File apuri = new File("/home/piakumpu/Dropbox/Public/Yliopisto/HY11-12/tiraharkka/apu.txt");
        
        try {
            Scanner lukija = new Scanner(apuri);
            while (lukija.hasNextLine()) {
                lauseet.add(lukija.nextLine()); 
                sanat();
            }     
            
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int i =0;
        
        while (i < sanat.size()) {
            System.out.println(sanat.get(i));
            i=i+1;
        }
          i =0;
        
        while (i < lauseet.size()) {
            System.out.println(lauseet.get(i));
            i=i+1;
        }
    }
    
    public static void sanat() {
        
        StringTokenizer lause = new StringTokenizer(lauseet.get(lauseet.size()-1)," ");
        while (lause.hasMoreTokens()) {
                sanat.add(trimmeri(lause.nextToken()));
        }
    
    }
    
    public static String trimmeri(String sana) {
           
        sana = sana.replace(",", "");
        sana = sana.replace("´", "");
        sana = sana.replace(";", "");
        sana = sana.replace(":", "");
        sana = sana.replace("?", "");
        sana = sana.replace("!", "");
        sana = sana.replace("'", "");
        
        sana= sana.toLowerCase();
        
        return sana;
    }
    
    static TrieNode createTree()
    {
        return(new TrieNode('\0', false));
    }
   
    static void insertWord(TrieNode root, String word)
    {
        int offset = 97;
        int l = word.length();
        char[] letters = word.toCharArray();
        TrieNode curNode = root;
       
        for (int i = 0; i < l; i++)
        {
            if (curNode.links[letters[i]-offset] == null)
                curNode.links[letters[i]-offset] = new TrieNode(letters[i], i == l-1 ? true : false);
            curNode = curNode.links[letters[i]-offset];
        }
    }

    static boolean find(TrieNode root, String word)
    {
        char[] letters = word.toCharArray();
        int l = letters.length;
        int offset = 97;
        TrieNode curNode = root;
       
        int i;
        for (i = 0; i < l; i++)
        {
            if (curNode == null)
                return false;
            curNode = curNode.links[letters[i]-offset];
        }
       
        if (i == l && curNode == null)
            return false;
       
        if (curNode != null && !curNode.fullWord)
            return false;
       
        return true;
    }
   
    static void printTree(TrieNode root, int level, char[] branch)
    {
        if (root == null)
            return;
       
        for (int i = 0; i < root.links.length; i++)
        {
            branch[level] = root.letter;
            printTree(root.links[i], level+1, branch);   
        }
       
        if (root.fullWord)
        {
            for (int j = 1; j <= level; j++)
                System.out.print(branch[j]);
            System.out.println();
        }
    }
   
    

    
    public static void main(String[] args) {
        
        System.out.println("Anna tutkittava teksti: ");
        lukukone();
        
        TrieNode tree = createTree();
         int i=0;  
        while (i < sanat.size()){
            insertWord(tree, sanat.get(i));
            i=i+1;
        }
       
        char[] branch = new char[50];
        printTree(tree, 0, branch);
       
        String searchWord = "all";
        if (find(tree, searchWord))
        {
            System.out.println("The word was found");
        }
        else
        {
            System.out.println("The word was NOT found");
        }
    
        
    }
}
    class TrieNode
{
    char letter;
    TrieNode[] links;
    boolean fullWord;
    TrieNode() {}
   
    TrieNode(char letter, boolean fullWord)
    {
        this.letter = letter;
        links = new TrieNode[26];
        this.fullWord = fullWord;
    }
}



