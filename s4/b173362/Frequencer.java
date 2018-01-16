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
        int i=left; int j=right;
        int pivot=(left+right)/2;
        if(left<right){
            while(true){
                while(suffixCompare(i,pivot)==-1){i++;}
                while(suffixCompare(pivot,j)==-1){j--;}
                if(i>=j)break;
                int tmp=suffixArray[j];
                suffixArray[j]=suffixArray[i];
                suffixArray[i]=tmp;
                i++;j--;
            }
        quicksort(left,i-1);
        quicksort(j+1,right);
        }
    }

    private int targetCompare(int i, int start, int end) {
        int s1=suffixArray[i];//myspaceの開始位置
        int s2=end-start;//mytargetの文字数
        if(s2 > mySpace.length-s1) return -1;//target_start_endのほうが長い時
        for(int k=0;k<s2;k++) {//target_start_endの文字数分文字列の比較
            if(mySpace[s1+k]>myTarget[start+k]) return 1;
            if(mySpace[s1+k]<myTarget[start+k]) return -1;
        }
        return 0;
    }

    private int subByteStartIndex(int start, int end) {
        
        //線形探索
        /*
        for(int i = 0;i<mySpace.length;i++){
            if(targetCompare(i,start,end)==0) return i;
        }
        return suffixArray.length;
        //線形探索
        */
        int left=0;
        int right=suffixArray.length;
        while(left<right){
            int mid=(left+right)/2;
            if(targetCompare(mid,start,end)==0){
                while(targetCompare(mid,start,end)==0&&mid>=0){
                    mid--;
                }
                return mid+1;
            }
            else if(targetCompare(mid,start,end)==1){
                right=mid;
            }
            else{
                left=mid+1;
            }
        }
            return suffixArray.length;
    }

    private int subByteEndIndex(int start, int end) {
        /*
        int j = -1;
        for(int i = mySpace.length-1;i>=0;i--){
            if(targetCompare(i,start,end)==0) return i+1;
        }
        return suffixArray.length;*/
        int left=0;
        int right=suffixArray.length;
        while(left<right){
            int mid=(left+right)/2;
            if(targetCompare(mid,start,end)==0){
                while(targetCompare(mid,start,end)==0&&mid<suffixArray.length){
                    mid++;
                }
                return mid;
            }
            else if(targetCompare(mid,start,end)==1){
                right=mid;
            }
            else{
                left=mid+1;
            }
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
            frequencerObject.setSpace("Hi Ho Hi Ho".getBytes());
            frequencerObject.setTarget("H".getBytes());
            int result = frequencerObject.frequency();
            System.out.print("Freq = "+ result+" ");
            if(4 == result) { System.out.println("OK"); }
            else {System.out.println("WRONG"); }
        }
        catch(Exception e) {
            System.out.println("STOP");
        }
    }
}
