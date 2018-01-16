public class Frequencer{
    //public class Frequencer implements FrequencerInterface{
    // Code to start with: This code is not working, but good start point to work.
    byte [] myTarget;
    byte [] mySpace;
    boolean targetReady = false;
    boolean spaceReady = false;
    int [] suffixArray;
    
    // The variable, "suffixArray" is the sorted array of all suffixes of mySpace.
    // Each suffix is expressed by a interger, which is the starting position in mySpace. // The following is the code to print the variable
    
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
        
	//i番目の文字列
	/*int s1=suffixArray[i];
	byte []suffix1=new byte[mySpace.length-s1];
        for(int k=s1;k<mySpace.length;k++) {
	    suffix1[k-s1]=mySpace[k];
	}
	
	//j番目の文字列
	int s2=suffixArray[j];
	byte []suffix2=new byte[mySpace.length-s2];
        for(int l=s2;l<mySpace.length;l++) {
	    suffix2[l-s2]=mySpace[l];
	}	
        String string1 = new String(suffix1);
        String string2 = new String(suffix2);
        
        if(string1.compareTo(string2)>0) return 1;
        else if(string1.compareTo(string2)<0) return -1;
        else return 0;*/
    }
    
public void setSpace(byte []space) {
    mySpace = space;
    if(mySpace.length>0) spaceReady = true;
    suffixArray = new int[space.length];
    // put all suffixes in suffixArray. Each suffix is expressed by one interger.
    for(int i = 0; i< space.length; i++) {
    suffixArray[i] = i;
    }

    for(int i=0; i<space.length;i++){
	for(int j=i+1;j<space.length;j++){
        
	    if(suffixCompare(i,j)==1){
		int tmp=suffixArray[j];
		suffixArray[j]=suffixArray[i];
		suffixArray[i]=tmp;
	    }
	}
        //System.out.println(i+"回目");
          //  printSuffixArray();
    }
   /* for(int i=0; i<space.length;i++){
        System.out.println(suffixArray[i]);
    }*/
    

    // Sorting is not implmented yet. /* Example from "Hi Ho Hi Ho"
    printSuffixArray();
}


    private int targetCompare(int i, int j, int end) {
// comparing suffix_i and target_j_end by dictonary order with limitation of length;
// if the beginning of suffix_i matches target_i_end, and suffix is longer than target it returns 0;
// if suffix_i > target_i_end it return 1;
// if suffix_i < target_i_end it return -1
// It is not implemented yet.
// It should be used to search the apropriate index of some suffix. // Example of search
// suffix
// "o"
// "o"
// "o"
// "o"
// "Ho" // "Ho" // "Ho" //"Ho" // "Ho" return 0;
        
        int s1=suffixArray[i];
        int s2=end-j;
        int s=0;
        if(s1 > s) s=s1;
        if(s2 > s) return -1;
        int n = end-j;
        for(int k=0;k<n;k++) {
            if(mySpace[s1+k]>myTarget[j+k]) return 1;
            if(mySpace[s1+k]<myTarget[j+k]) return -1;
        }
        return 0;
        
        /*int s1=suffixArray[i];
        byte []suffix1=new byte[end-j];
        for(int k=s1;k<end-j;k++) {
            suffix1[k-s1]=mySpace[k];
        }
        
        //j番目の文字列
        byte []target=new byte[end-j];
        for(int l=j;l<end-j;l++) {
            target[l-j]=myTarget[l];
        }
        
        String string1 = new String(suffix1);
        String string2 = new String(target);
        
        if(string1.compareTo(string2)>0) return 1;
        else if(string1.compareTo(string2)<0) return -1;
        else return 0;*/

}

private int subByteStartIndex(int start, int end) {
// It returns the index of the first suffix which is equal or greater than subBytes; // not implemented yet;
// For "Ho", it will return 5 for "Hi Ho Hi Ho".
// For "Ho ", it will return 6 for "Hi Ho Hi Ho".
    for(int i = 0;i<mySpace.length;i++){
    if(targetCompare(i,start,end)==0) return i;
    }
return suffixArray.length; }

private int subByteEndIndex(int start, int end) {
// It returns the next index of the first suffix which is greater than subBytes; // not implemented yet
// For "Ho", it will return 7 for "Hi Ho Hi Ho".
// For "Ho ", it will return 7 for "Hi Ho Hi Ho".
    int j = -1;
    for(int i = mySpace.length-1;i>=0;i--){
        if(targetCompare(i,start,end)==0) return i+1;
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
 if(myTarget[start+i] != mySpace[offset+i]) { abort = true; break; } }
 if(abort == false) { count++; } }
 
int first = subByteStartIndex(start,end);
int last1 = subByteEndIndex(start, end);
/* inspection code*/
 for(int k=start;k<end;k++) { System.out.write(myTarget[k]); } System.out.printf(": first=%d last1=%d\n", first, last1);
 
return last1 - first;
}
public void setTarget(byte [] target) {
myTarget = target; if(myTarget.length>0) targetReady = true; }
public int frequency() {
if(targetReady == false) return -1; if(spaceReady == false) return 0;
return subByteFrequency(0, myTarget.length);
}

public static void main(String[] args) { Frequencer frequencerObject;
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
System.out.println("STOP"); }
} }
