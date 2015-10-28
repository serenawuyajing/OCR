package edu.dal.mibio.corr.corrector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.dal.mibio.corr.perfectHash.*;
import gnu.trove.map.hash.TObjectIntHashMap;

public class Google5gram {
	
    private static final int LEN_CONTEXTS = 16;
    private static final int LEN_DB = 1000000;
	
	public static int[] transferContexts(String[] contexts)
	{
		int[] contextsInt = new int[4];
		for(int i=0;i<contexts.length;i++)
		{
			if(PhUnigram.hash_unigram_Dictionary.containsKey(contexts[i]))
			{
				contextsInt[i] = PhUnigram.hash_unigram_Dictionary.get(contexts[i]);
			}
			else
			{
				contextsInt = null;
				break;
			}
		}
		return  contextsInt;
	}
	
	public static List<PhashValues> searchDB(char firstCharacter, int phValue, int can)
	{
		List<PhashValues> phs = new ArrayList<PhashValues>();
		int fileNumber = phValue/LEN_DB;
		String fileStr = String.format("%04d",fileNumber);
		int tmp = firstCharacter;
		String dbName = "";
		if(tmp >= 65 && tmp <= 90)
		{
			dbName = "U"+firstCharacter+"_"+fileStr;
		}
		else
		{
			dbName = firstCharacter+"_"+fileStr;
		}
		
		Connection c = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost/yajing_nlp", 
		            "yajing", "yajing");
		    String sql  = "select * from "+dbName+" where linenumber = "+phValue+" and "+"candidate = "+can;
		    PreparedStatement ps = c.prepareStatement(sql);
		    ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				PhashValues ph = new PhashValues(rs.getInt("candidate"),rs.getInt("position"),rs.getLong("frequency"));
				phs.add(ph);
			}
			
			if(ps != null){
				ps.close();
			}
			if(rs != null){
				rs.close();
			}
			if(c != null){
				c.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return phs;
	}
	
	public static int calPhValue(int[] k, char firstCharacter,List<List<Integer>> tabValues)
	{
		int phValue = -1;
				 
		if(firstCharacter >= 48 && firstCharacter <= 57)
		{
			PerfectHash_Number ph_number = new PerfectHash_Number();
			phValue = ph_number.phash(k, LEN_CONTEXTS,tabValues);
		}
		else if(!((firstCharacter >= 65 && firstCharacter <= 90)||(firstCharacter >= 97 && firstCharacter <= 122)))
		{
			 PerfectHash_Punctuation ph_punctuation = new PerfectHash_Punctuation();
			 phValue = ph_punctuation.phash(k, LEN_CONTEXTS,tabValues);
		}
		else
		{
			 switch(firstCharacter)
			 {
			   case 65: PerfectHash_Upper_A ph_A = new PerfectHash_Upper_A(); phValue = ph_A.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 66: PerfectHash_Upper_B ph_B = new PerfectHash_Upper_B(); phValue = ph_B.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 67: PerfectHash_Upper_C ph_C = new PerfectHash_Upper_C(); phValue = ph_C.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 68: PerfectHash_Upper_D ph_D = new PerfectHash_Upper_D(); phValue = ph_D.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 69: PerfectHash_Upper_E ph_E = new PerfectHash_Upper_E(); phValue = ph_E.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 70: PerfectHash_Upper_F ph_F = new PerfectHash_Upper_F(); phValue = ph_F.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 71: PerfectHash_Upper_G ph_G = new PerfectHash_Upper_G(); phValue = ph_G.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 72: PerfectHash_Upper_H ph_H = new PerfectHash_Upper_H(); phValue = ph_H.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 73: PerfectHash_Upper_I ph_I = new PerfectHash_Upper_I(); phValue = ph_I.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 74: PerfectHash_Upper_J ph_J = new PerfectHash_Upper_J(); phValue = ph_J.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 75: PerfectHash_Upper_K ph_K = new PerfectHash_Upper_K(); phValue = ph_K.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 76: PerfectHash_Upper_L ph_L = new PerfectHash_Upper_L(); phValue = ph_L.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 77: PerfectHash_Upper_M ph_M = new PerfectHash_Upper_M(); phValue = ph_M.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 78: PerfectHash_Upper_N ph_N = new PerfectHash_Upper_N(); phValue = ph_N.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 79: PerfectHash_Upper_O ph_O = new PerfectHash_Upper_O(); phValue = ph_O.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 80: PerfectHash_Upper_P ph_P = new PerfectHash_Upper_P(); phValue = ph_P.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 81: PerfectHash_Upper_Q ph_Q = new PerfectHash_Upper_Q(); phValue = ph_Q.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 82: PerfectHash_Upper_R ph_R = new PerfectHash_Upper_R(); phValue = ph_R.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 83: PerfectHash_Upper_S ph_S = new PerfectHash_Upper_S(); phValue = ph_S.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 84: PerfectHash_Upper_T ph_T = new PerfectHash_Upper_T(); phValue = ph_T.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 85: PerfectHash_Upper_U ph_U = new PerfectHash_Upper_U(); phValue = ph_U.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 86: PerfectHash_Upper_V ph_V = new PerfectHash_Upper_V(); phValue = ph_V.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 87: PerfectHash_Upper_W ph_W = new PerfectHash_Upper_W(); phValue = ph_W.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 88: PerfectHash_Upper_X ph_X = new PerfectHash_Upper_X(); phValue = ph_X.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 89: PerfectHash_Upper_Y ph_Y = new PerfectHash_Upper_Y(); phValue = ph_Y.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 90: PerfectHash_Upper_Z ph_Z = new PerfectHash_Upper_Z(); phValue = ph_Z.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 97: PerfectHash_a ph_a = new PerfectHash_a(); phValue = ph_a.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 98: PerfectHash_b ph_b = new PerfectHash_b(); phValue = ph_b.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 99: PerfectHash_c ph_c = new PerfectHash_c(); phValue = ph_c.phash(k, LEN_CONTEXTS,tabValues); break;
			   case 100: PerfectHash_d ph_d = new PerfectHash_d(); phValue = ph_d.phash(k, LEN_CONTEXTS,tabValues);break;
			   case 101: PerfectHash_e ph_e = new PerfectHash_e(); phValue = ph_e.phash(k, LEN_CONTEXTS,tabValues);break;
			   case 102: PerfectHash_f ph_f = new PerfectHash_f(); phValue = ph_f.phash(k, LEN_CONTEXTS,tabValues);break;
			   case 103: PerfectHash_g ph_g = new PerfectHash_g(); phValue = ph_g.phash(k, LEN_CONTEXTS,tabValues);break;
			   case 104: PerfectHash_h ph_h = new PerfectHash_h(); phValue = ph_h.phash(k, LEN_CONTEXTS,tabValues);break;
			   case 105: PerfectHash_i ph_i = new PerfectHash_i(); phValue = ph_i.phash(k, LEN_CONTEXTS,tabValues);break;
			   case 106: PerfectHash_j ph_j = new PerfectHash_j(); phValue = ph_j.phash(k, LEN_CONTEXTS,tabValues);break;
			   case 107: PerfectHash_k ph_k = new PerfectHash_k(); phValue = ph_k.phash(k, LEN_CONTEXTS,tabValues);break;
			   case 108: PerfectHash_l ph_l = new PerfectHash_l(); phValue = ph_l.phash(k, LEN_CONTEXTS,tabValues);break;
			   case 109: PerfectHash_m ph_m = new PerfectHash_m(); phValue = ph_m.phash(k, LEN_CONTEXTS,tabValues);break;
			   case 110: PerfectHash_n ph_n = new PerfectHash_n(); phValue = ph_n.phash(k, LEN_CONTEXTS,tabValues);break;
			   case 111: PerfectHash_o ph_o = new PerfectHash_o(); phValue = ph_o.phash(k, LEN_CONTEXTS,tabValues);break;
			   case 112: PerfectHash_p ph_p = new PerfectHash_p(); phValue = ph_p.phash(k, LEN_CONTEXTS,tabValues);break;
			   case 113: PerfectHash_q ph_q = new PerfectHash_q(); phValue = ph_q.phash(k, LEN_CONTEXTS,tabValues);break;
			   case 114: PerfectHash_r ph_r = new PerfectHash_r(); phValue = ph_r.phash(k, LEN_CONTEXTS,tabValues);break;
			   case 115: PerfectHash_s ph_s = new PerfectHash_s(); phValue = ph_s.phash(k, LEN_CONTEXTS,tabValues);break;
			   case 116: PerfectHash_t ph_t = new PerfectHash_t(); phValue = ph_t.phash(k, LEN_CONTEXTS,tabValues);break;
			   case 117: PerfectHash_u ph_u = new PerfectHash_u(); phValue = ph_u.phash(k, LEN_CONTEXTS,tabValues);break;
			   case 118: PerfectHash_v ph_v = new PerfectHash_v(); phValue = ph_v.phash(k, LEN_CONTEXTS,tabValues);break;
			   case 119: PerfectHash_w ph_w = new PerfectHash_w(); phValue = ph_w.phash(k, LEN_CONTEXTS,tabValues);break;
			   case 120: PerfectHash_x ph_x = new PerfectHash_x(); phValue = ph_x.phash(k, LEN_CONTEXTS,tabValues);break;
			   case 121: PerfectHash_y ph_y = new PerfectHash_y(); phValue = ph_y.phash(k, LEN_CONTEXTS,tabValues);break;
			   case 122: PerfectHash_z ph_z = new PerfectHash_z(); phValue = ph_z.phash(k, LEN_CONTEXTS,tabValues);break;
			   default: phValue = -1; break;
			 }
		}
		
	    return phValue;
	}
	
	 public static List<PhashValues> getPhValues(String word,String[] contexts,List<List<Integer>> tabValues){
		 List<PhashValues> phs = new ArrayList<PhashValues>();
		 int can = PhUnigram.hash_unigram_Dictionary.get(word);
		 System.out.println(can);
		 if(can != 0)
		 {
			 char firstCharacter = contexts[0].charAt(0);
			 int[] contextsInt = Google5gram.transferContexts(contexts);
			 for(int i=0;i<contextsInt.length;i++)
			 {
				 System.out.println(contextsInt[i]);
			 }
			 if(contextsInt.length > 0 && contextsInt != null)
			{
				int[] k = Lookupa.intToCharArray(contextsInt);
				int phValue = calPhValue(k,firstCharacter,tabValues);
				System.out.println(firstCharacter+" "+phValue+" "+can);
				if(phValue != -1)
				{
					phs = searchDB(firstCharacter,phValue,can);
				}
			} 
		 }
		 
		return phs;
	 }
}
