/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mohan
 */
public class Nox {
    public static void main(String args[]){
        Nox noa= new Nox();
        String stra="codex";
        System.out.println(noa.nox(stra));
        
    }
    public String nox(String str){
        String star="y";
        if(str.length()==0){
            return "";
        }
        if(str.charAt(0)!='x'){
            return (str.charAt(0) +nox(str.substring(1)));
        }
        else{
            return star+nox(str.substring(1));
        }
    }
    
}
