package edu.dal.mibio.corr.perfectHash;

public class Lookupa {
	public static final int CHECKSTATE = 8;
	
	public static int[] checkSum(int k[],int len,int[] state)
	{
		int[] returnStates = new int[8];
		int length = len;
		int[] mixList = new int[8];
		int a = state[0];
		int b = state[1];
		int c = state[2];
		int d = state[3];
		int e = state[4];
		int f = state[5];
		int g = state[6];
		int h = state[7];
		int index =0;
		while(len >= 32)
		{
		  a += (k[index++] +(k[index++]<<8) +(k[index++]<<16) +(k[index++]<<24));
	      b += (k[index++] +(k[index++]<<8) +(k[index++]<<16) +(k[index++]<<24));
	      c += (k[index++] +(k[index++]<<8) +(k[index++]<<16)+(k[index++]<<24));
	      d += (k[index++]+(k[index++]<<8)+(k[index++]<<16)+(k[index++]<<24));
	      e += (k[index++]+(k[index++]<<8)+(k[index++]<<16)+(k[index++]<<24));
	      f += (k[index++]+(k[index++]<<8)+(k[index++]<<16)+(k[index++]<<24));
	      g += (k[index++]+(k[index++]<<8)+(k[index++]<<16)+(k[index++]<<24));
	      h += (k[index++]+(k[index++]<<8)+(k[index++]<<16)+(k[index++]<<24));
	      mixList = mixc(a,b,c,d,e,f,g,h);
	      a = mixList[0];
	      b = mixList[1];
	      c = mixList[2];
	      d = mixList[3];
	      e = mixList[4];
	      f = mixList[5];
	      g = mixList[6];
	      h = mixList[7];
	      mixList = mixc(a,b,c,d,e,f,g,h);
	      a = mixList[0];
	      b = mixList[1];
	      c = mixList[2];
	      d = mixList[3];
	      e = mixList[4];
	      f = mixList[5];
	      g = mixList[6];
	      h = mixList[7];
	      mixList = mixc(a,b,c,d,e,f,g,h);
	      a = mixList[0];
	      b = mixList[1];
	      c = mixList[2];
	      d = mixList[3];
	      e = mixList[4];
	      f = mixList[5];
	      g = mixList[6];
	      h = mixList[7];
	      mixList = mixc(a,b,c,d,e,f,g,h);
	      a = mixList[0];
	      b = mixList[1];
	      c = mixList[2];
	      d = mixList[3];
	      e = mixList[4];
	      f = mixList[5];
	      g = mixList[6];
	      h = mixList[7];
	      len -= 32;
		}
	   h += length;
	   switch(len)
	   {
		   case 31: h+=(k[30]<<24);
		   case 30: h+=(k[29]<<16);
		   case 29: h+=(k[28]<<8);
		   case 28: g+=(k[27]<<24);
		   case 27: g+=(k[26]<<16);
		   case 26: g+=(k[25]<<8);
		   case 25: g+=k[24];
		   case 24: f+=(k[23]<<24);
		   case 23: f+=(k[22]<<16);
		   case 22: f+=(k[21]<<8);
		   case 21: f+=k[20];
		   case 20: e+=(k[19]<<24);
		   case 19: e+=(k[18]<<16);
		   case 18: e+=(k[17]<<8);
		   case 17: e+=k[16];
		   case 16: d+=(k[15]<<24);
		   case 15: d+=(k[14]<<16);
		   case 14: d+=(k[13]<<8);
		   case 13: d+=k[12];
		   case 12: c+=(k[11]<<24);
		   case 11: c+=(k[10]<<16);
		   case 10: c+=(k[9]<<8);
		   case 9 : c+=k[8];
		   case 8 : b+=(k[7]<<24);
		   case 7 : b+=(k[6]<<16);
		   case 6 : b+=(k[5]<<8);
		   case 5 : b+=k[4];
		   case 4 : a+=(k[3]<<24);
		   case 3 : a+=(k[2]<<16);
		   case 2 : a+=(k[1]<<8);
		   case 1 : a+=k[0];
	   }
	   mixList = mixc(a,b,c,d,e,f,g,h);
	      a = mixList[0];
	      b = mixList[1];
	      c = mixList[2];
	      d = mixList[3];
	      e = mixList[4];
	      f = mixList[5];
	      g = mixList[6];
	      h = mixList[7];
       mixList = mixc(a,b,c,d,e,f,g,h);
          a = mixList[0];
	      b = mixList[1];
	      c = mixList[2];
	      d = mixList[3];
	      e = mixList[4];
	      f = mixList[5];
	      g = mixList[6];
	      h = mixList[7];
       mixList = mixc(a,b,c,d,e,f,g,h);
          a = mixList[0];
	      b = mixList[1];
	      c = mixList[2];
	      d = mixList[3];
	      e = mixList[4];
	      f = mixList[5];
	      g = mixList[6];
	      h = mixList[7];
       mixList = mixc(a,b,c,d,e,f,g,h);
          a = mixList[0];
	      b = mixList[1];
	      c = mixList[2];
	      d = mixList[3];
	      e = mixList[4];
	      f = mixList[5];
	      g = mixList[6];
	      h = mixList[7];
	   returnStates[0]=a; returnStates[1]=b; returnStates[2]=c; returnStates[3]=d;
	   returnStates[4]=e; returnStates[5]=f; returnStates[6]=g; returnStates[7]=h;
	   return returnStates;
	}
	
	public static int[] mixc(int a, int b,int c,int d,int e,int f,int g,int h)
	{
		  int[] mixList= new int[8];
		  a^=b<<11; d+=a; b+=c; 
		  b^=c>>>2;  e+=b; c+=d;
		  c^=d<<8;  f+=c; d+=e;
		  d^=e>>>16; g+=d; e+=f; 
		  e^=f<<10; h+=e; f+=g; 
		  f^=g>>>4;  a+=f; g+=h; 
		  g^=h<<8;  b+=g; h+=a; 
		  h^=a>>>9;  c+=h; a+=b; 
		  mixList[0]=a;
		  mixList[1]=b;
		  mixList[2]=c;
		  mixList[3]=d;
		  mixList[4]=e;
		  mixList[5]=f;
		  mixList[6]=g;
		  mixList[7]=h;
		  return mixList;
	}
	
	
	public static int[] intToCharArray(int[] contexts)
	{
		int[] k = new int[16];
		int i =0;
		while(i<16)
		{
			for(int j=0;j<contexts.length;j++)
			{
				k[i++] = (contexts[j] >> 24) & 0xFF;
			    k[i++] = (contexts[j]  >> 16) & 0xFF;
			    k[i++] = (contexts[j]  >> 8) & 0xFF;
			    k[i++] = contexts[j] & 0xFF;
			}
		}
		return k;
	}

}
