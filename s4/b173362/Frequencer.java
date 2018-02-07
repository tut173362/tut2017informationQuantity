package s4.b173362; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID.
import java.lang.*;
import s4.specification.*;

/*
 interface FrequencerInterface {     // This interface provides the design for frequency counter.
 void setTarget(byte[]  target); // set the data to search.
 void setSpace(byte[]  space);  // set the data to be searched target from.
 int frequency(); //It return -1, when TARGET is not set or TARGET's length is zero
 //Otherwise, it return 0, when SPACE is not set or Space's length is zero
 //Otherwise, get the frequency of TAGET in SPACE
 int subByteFrequency(int start, int end);
 // get the frequency of subByte of taget, i.e target[start], taget[start+1], ... , target[end-1].
 // For the incorrect value of START or END, the behavior is undefined.
 
*/
public class Frequencer implements FrequencerInterface{
    byte [] myTarget;
    byte [] mySpace;
    boolean targetReady = false;
    boolean spaceReady = false;
    int [] suffixArray;
    
    private void printSuffixArray() {
        if(spaceReady) {
            for(int i=0; i< mySpace.length; i++) {
                int s = suffixArray[i];
                System.out.print(i+":");
                for(int j=s;j<mySpace.length;j++) {
                    System.out.write(mySpace[j]); }
                System.out.write('\n'); }
        }
    }
    
    private int suffixCompare(int i, int j) {
        
    int s1=suffixArray[i];
    int s2=suffixArray[j];
        int s=0;
        if(s1 > s) s=s1;
        if(s2 > s) s=s2;
        int n = mySpace.length -s;
              for(int k=0;k<n;k++) {
                  if(mySpace[s1+k]>mySpace[s2+k]) return 1;
                  if(mySpace[s1+k]<mySpace[s2+k]) return -1;
              }
              if(s1 < s2) return 1;
              if(s1 > s2) return -1;
              return 0;
    }
    
    public void setSpace(byte []space) {
        mySpace = space;
        if(mySpace.length>0) spaceReady = true;
        suffixArray = new int[space.length];
        // put all suffixes in suffixArray. Each suffix is expressed by one interger.
        for(int i = 0; i< space.length; i++) {
            suffixArray[i] = i;
        }
//////バブルソート///
        /*
        for(int i=0; i<space.length;i++){
            for(int j=i+1;j<space.length;j++){
        
                if(suffixCompare(i,j)==1){
                    int tmp=suffixArray[j];
                    suffixArray[j]=suffixArray[i];
                    suffixArray[i]=tmp;
                }
            }
        }*/
//////バブルソート////
        quicksort(0,mySpace.length-1);
        printSuffixArray();
    }
    
    public void quicksort(int left, int right){
        int pivot;
        if(left<right){
            pivot=part(left,right);
            quicksort(left,pivot);
            quicksort(pivot+1,right);
        }
    }
    public int part(int left, int right){
        int leftwall=left;
        for(int i=left+1;i<=right;i++){
            if(suffixCompare(i,left)==-1){
                leftwall+=1;
                swap(i,leftwall);
            }
        }
        swap(left,leftwall);
        return leftwall;
    }
    
    
    public void swap(int i, int j){
        int tmp=suffixArray[i];
        suffixArray[i]=suffixArray[j];
        suffixArray[j]=tmp;
    }
    

    private int targetCompare(int i, int start, int end) {
        int s1=suffixArray[i];//myspaceの開始位置
        int s2=end-start;//mytargetの文字数
        for(int k=0;k<s2;k++) {//target_start_endの文字数分文字列の比較
            if(s1+k<mySpace.length){
                if(mySpace[s1+k]>myTarget[start+k]) return 1;
                if(mySpace[s1+k]<myTarget[start+k]) return -1;
            }
            else return -1;
        }
        //if(s2 > mySpace.length-s1) return -1;//target_start_endのほうが長い
        return 0;
    }

    private int subByteStartIndex(int start, int end) {
        int left=0;
        int right=suffixArray.length-1;
        int mid = 0;
        do{
            mid=(left+right)/2;
            //System.out.println("left:"+left+"right:"+right+"mid:"+mid);
            //System.out.println(targetCompare(mid,start,end));
            if(targetCompare(mid,start,end)==0){
                //System.out.println("center");
                right=mid-1;
            }
            else if(targetCompare(mid,start,end)==-1){
                //System.out.println("right");
                left=mid+1;
            }
            else if(targetCompare(mid,start,end)==1){
                //System.out.println("left");
                right=mid-1;
            }
            else{
                //System.out.println("error");
                return  suffixArray.length;}
            
        }while(right-left>1);
        
        if(targetCompare(left,start,end)==0){
            return left;
        }
        else if(targetCompare(right,start,end)==0){
            return right;
        }
        return suffixArray.length;
    }
    

    private int subByteEndIndex(int start, int end) {
        int left=0;
        int right=suffixArray.length-1;
        int mid = 0;
        do{
            mid=(left+right)/2;
            //System.out.println("left:"+left+"right:"+right+"mid:"+mid);
            //System.out.println(targetCompare(mid,start,end));

            if(targetCompare(mid,start,end)==0){
                left=mid+1;
            }
            else if(targetCompare(mid,start,end)==-1){
                left=mid+1;
            }
            else if(targetCompare(mid,start,end)==1){
                right=mid-1;
            }
            else{return  suffixArray.length;}
        }while(left<right);
        
        if(targetCompare(right,start,end)==0){
            return right+1;
        }
        else if(targetCompare(left,start,end)!=0){
            return left+1;
        }
        return suffixArray.length;
    }

    public int subByteFrequency(int start, int end) {
        /* This method could be defined as follows though it is slow.*/
        int spaceLength = mySpace.length;
        int count = 0;
        for(int offset = 0; offset< spaceLength - (end - start); offset++) {
            boolean abort = false;
            for(int i = 0; i< (end - start); i++) {
                if(myTarget[start+i] != mySpace[offset+i]) {
                    abort = true; break;
                }
            }
            if(abort == false) { count++; }
        }
 
        int first = subByteStartIndex(start,end);
        int last1 = subByteEndIndex(start, end);
        
        /* inspection code*/
        for(int k=start;k<end;k++) {
            System.out.write(myTarget[k]);
        }
        System.out.printf(": first=%d last1=%d\n", first, last1);
 
        return last1 - first;
    }
    
    
    public void setTarget(byte [] target) {
        myTarget = target;
        if(myTarget.length>0)
            targetReady = true;
    }
    
    public int frequency() {
        if(targetReady == false) return -1;
        if(spaceReady == false) return 0;
        return subByteFrequency(0, myTarget.length);
    }

    public static void main(String[] args) {
        Frequencer frequencerObject;
        try {
    
            frequencerObject = new Frequencer();
            frequencerObject.setSpace("Hi Ho Hi Ho Hi Ho Hi Ho Hi Ho Hi Ho Hi Ho Hi Ho".getBytes());
            frequencerObject.setTarget("o Hi".getBytes());
            int result = frequencerObject.frequency();
            System.out.print("Freq = "+ result+" ");
            if(4 == result) { System.out.println("OK"); }
            else {System.out.println("WRONG"); }
        }
        catch(Exception e) {
            //System.out.println("STOP");
            System.out.println(e);
        }
    }
}
