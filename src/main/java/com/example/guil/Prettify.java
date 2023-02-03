package com.example.guil;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class Prettify {


    public void parsing_xml_with_array(String sa,ArrayList<String> string,int [] arr) {
        char space=32;
        int index=-1;
        for (int i = 0; i < sa.length(); i++) {
            if (sa.charAt(i)==space||sa.charAt(i)=='\r'||sa.charAt(i)=='\n')continue;

            else if (sa.charAt(i)=='<') {

                int j=i;

                while ((sa.charAt(i)!='>')) {
                    i++;
                }
                if (sa.charAt(j+1)=='/'){

                    string.add(sa.substring(j,i+1));
                    index=string.indexOf(sa.substring(j,i+1));
                    arr[index]=3;
                    if(arr[index]==0){
                        arr[index]=3;
                    }
                }
                else {
                    string.add(sa.substring(j,i+1));

                    index=string.indexOf(sa.substring(j,i+1));
                    arr[index]=1;
                    if(arr[index]==0){
                        arr[index]=1;}
                }
            }
            else {
                int k=i;
                while((sa.charAt(i)!='<')) {

                    if (sa.charAt(i)=='\r'||sa.charAt(i)=='\n')break;
                    i++;}
                string.add(sa.substring(k,i));
                index =string.indexOf(sa.substring(k,i));

                arr[index]=2;
                if(arr[index]==0){arr[index]=2; }

                --i;
            }

        }

    }
    public  void Check_ParsingArray(int []arr,ArrayList<String> string){

        for(int i=0;i<string.size();i++){

            if(arr[i]==0){
                Parse_error(string,i,arr);
            }
        }
    }

    public  void Parse_error(ArrayList<String> string,int index,int []arr){
        String str;
        str=string.get(index);
        if(str.charAt(0)=='<'){
            arr[index]=1;
            if(str.charAt(1)=='/'){
                arr[index]=3;}
        }
        else{arr[index]=2;}
    }

    public  void writting(String input,StringBuilder output,int spaces,boolean flag){


        if(flag==false){ //openning tag
            output.append("\n");
            for(int i=0;i<spaces;i++){
                output.append(" ");
            }

            output.append(input);

        }if(flag==true){
            output.append(input);
        }
    }


    public  void pretiffy(int []arr,ArrayList<String> string,StringBuilder output){
        int indentation=-1;
        boolean flag=false;

        String str;
        int max=string.size();
        int x=0;
        if(string.size()>arr.length){max=arr.length;}

        for(int i=0;i<max;i++){

            if(arr[i]==1){
                indentation=indentation+3;
                flag=false;
                writting(string.get(i),output,indentation,flag);
            }else if(arr[i]==2){str=string.get(i);
                if(str.length()>10){
                    flag=false;}else{flag=true;x=1;}
                if(str.length()>50 && str.length()<=150){String str1,str2;
                    int mid;
                    mid=str.length()/2;
                    while(str.charAt(mid)!= ' '){
                        mid++;
                    }str1=str.substring(0, mid);
                    writting(str1,output,indentation,flag);
                    str2=str.substring(mid);
                    writting(str2,output,indentation,flag);
                    continue;}else if(str.length()>150 && str.length()<=240){String str1,str2,str3;
                    int fthird,sthird;
                    fthird=str.length()/3;
                    sthird=str.length()-fthird;
                    while(str.charAt(fthird)!=' '){
                        fthird++;
                    }while(str.charAt(sthird)!=' '){
                        sthird++;
                    }
                    str1=str.substring(0, fthird);
                    writting(str1,output,indentation,flag);
                    str2=str.substring(fthird, sthird);
                    writting(str2,output,indentation,flag);

                    str3=str.substring(sthird);
                    writting(str3,output,indentation,flag);
                    continue;
                }else if(str.length()>240){String str1,str2,str3,str4;
                    int fquarter,squarter,tquarter;
                    fquarter=str.length()/4;
                    squarter=fquarter*2;
                    tquarter=fquarter*3;
                    while(str.charAt(fquarter)!=' '){
                        fquarter++;
                    }while(str.charAt(squarter)!=' '){
                        squarter++;
                    }while(str.charAt(tquarter)!=' '){
                        tquarter++;
                    }
                    str1=str.substring(0, fquarter);
                    writting(str1,output,indentation,flag);
                    str2=str.substring(fquarter, squarter);
                    writting(str2,output,indentation,flag);
                    str3=str.substring(squarter,tquarter);
                    writting(str3,output,indentation,flag);
                    str4=str.substring(tquarter);
                    writting(str4,output,indentation,flag);
                    continue;
                }

                writting(string.get(i),output,indentation,flag);
            }else if(arr[i]==3){
                if(x==1){flag=true;
                    x=0;
                }else if(arr[i]==3){
                    flag=false;}
                writting(string.get(i),output,indentation,flag);
                indentation=indentation-3;

            }
        }
    }
    
}
