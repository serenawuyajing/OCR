package edu.dal.mibio.corr.perfectHash;

public class PerfectHash_Upper_G extends PerfectHash{

	private static int[] state_upper_g = {0x78dde6e4,0x78dde6e4,0x78dde6e4,0x78dde6e4,0x78dde6e4,0x78dde6e4,0x78dde6e4,0x78dde6e4};
	private static int value_upper_g = 0xffffff;
	private static int index_upper_g = 8;
	private static int[] scramble_upper_g = {
		0x00000000, 0x01fd3433, 0x0149ee64, 0x0043b9f6,
		0x0184bb47, 0x0162ecf9, 0x003135dd, 0x00f05286,
		0x00e942ff, 0x0171c599, 0x0128323e, 0x00f61d10,
		0x007a8aaf, 0x000033b7, 0x00917cc7, 0x00f8dd5e,
		0x01b46fa6, 0x017af267, 0x01c5b369, 0x002f4855,
		0x0025b959, 0x01d32b58, 0x00123f16, 0x00e29484,
		0x00845f8c, 0x006de145, 0x0035ccc8, 0x00355e1a,
		0x0142ed15, 0x013e6db5, 0x017ccabc, 0x004def60,
		0x006a8408, 0x01742430, 0x005b55a9, 0x0020f606,
		0x0194d506, 0x01d6df36, 0x01f1011e, 0x01b20ddc,
		0x00f134df, 0x0090398d, 0x01cd567a, 0x01cd648e,
		0x00d17df2, 0x01c359a1, 0x01bdd1a3, 0x01c9d169,
		0x016aac95, 0x0024c3aa, 0x01f596ae, 0x016b2bce,
		0x01a436f1, 0x0133ed9c, 0x01231335, 0x0069b7f6,
		0x018a6a7f, 0x019297f4, 0x009846a6, 0x01f91716,
		0x01a8dce3, 0x009a9f2f, 0x00b7909c, 0x013132c7,
		0x010490ec, 0x013479db, 0x0197536c, 0x0181832d,
		0x00b74616, 0x00d2810f, 0x01199896, 0x0017eed3,
		0x012980f9, 0x017fd22b, 0x00368756, 0x01fa8343,
		0x001420cd, 0x0133b833, 0x01308508, 0x011fea32,
		0x0090d61d, 0x01f1008e, 0x012e78ab, 0x005035ab,
		0x001bcef4, 0x01f60889, 0x0147b660, 0x01f187b3,
		0x016f15e8, 0x01c4cfd5, 0x00f3ae5e, 0x004fc5bd,
		0x00208175, 0x01745691, 0x00a72509, 0x00390e8a,
		0x01de1ec2, 0x000b5373, 0x015210c9, 0x013685e6,
		0x00e68a95, 0x00e19fa2, 0x013f06f0, 0x01aace86,
		0x00eaf279, 0x01d4ccd4, 0x00a7bf1e, 0x01150662,
		0x001bc848, 0x00281a9a, 0x00c13073, 0x005456a1,
		0x0096efbc, 0x00a596a6, 0x018ccbbe, 0x01b784a5,
		0x00023a69, 0x0161a1f7, 0x00e0106b, 0x00c2688e,
		0x009ba736, 0x01535025, 0x000bb0bc, 0x01c5ea08,
		0x00177b68, 0x01a71c9f, 0x00c18053, 0x00b5b3c8,
		0x01dc6a35, 0x01d90a65, 0x01630818, 0x003c582f,
		0x00378070, 0x0071d1a0, 0x013e8225, 0x00a8dcc8,
		0x01db3bc8, 0x004a1e9c, 0x00aec21a, 0x00e7441a,
		0x0004febe, 0x00a5a96c, 0x0168c940, 0x000a9145,
		0x017f9d59, 0x0154d7cd, 0x01668eba, 0x00c531f4,
		0x000d81b0, 0x01d3aa96, 0x014e1261, 0x00a6e811,
		0x019048e5, 0x00df4827, 0x00570d17, 0x017e1156,
		0x017c51c4, 0x0054dbb4, 0x00cf9f7d, 0x00c578a5,
		0x0068528c, 0x01ac57e4, 0x0004de49, 0x01fad383,
		0x0040e98c, 0x00aecf9f, 0x00d6113d, 0x0077de22,
		0x0195c2e3, 0x0176e693, 0x00c4378f, 0x01047def,
		0x003311c0, 0x01db4f0a, 0x007bea00, 0x008eb641,
		0x00f0afbe, 0x01792331, 0x00ed5961, 0x01df8cc7,
		0x0146f681, 0x001d6215, 0x01657157, 0x01c90a03,
		0x01975b77, 0x0043d49f, 0x00553823, 0x005f5ec4,
		0x01f8ffd7, 0x01f2012b, 0x0057ad31, 0x00138b59,
		0x00d3dae6, 0x00ef1037, 0x005aa39d, 0x01e1e320,
		0x00b30150, 0x01d5c0b7, 0x0065d338, 0x0070b6b3,
		0x00753f5c, 0x00baeb0b, 0x001ba656, 0x00378946,
		0x017f84c6, 0x01b89e85, 0x019fb477, 0x01455f72,
		0x01e6ee5a, 0x00d35998, 0x01b7dae0, 0x0192fd38,
		0x00313fad, 0x012b85a6, 0x007a67ab, 0x01a1c2dc,
		0x005f43c0, 0x00624d66, 0x01e0859a, 0x005f1895,
		0x018e3cfc, 0x005b4233, 0x01d89d57, 0x00464bfd,
		0x00b328c9, 0x005ac4f2, 0x0108a618, 0x01e8fbfb,
		0x007a8833, 0x00e31de4, 0x00aa9caf, 0x01c07ce6,
		0x00ee8b00, 0x00708101, 0x01c56fa2, 0x009086c3,
		0x00761b4c, 0x010c2aa8, 0x01c95244, 0x0128e254,
		0x0137d2ff, 0x00216e88, 0x00a0feab, 0x00a9aff9,
		0x01092d56, 0x01c27e50, 0x016551b8, 0x0129b9f5,
		0x01eee15d, 0x011fc3da, 0x01b1cd33, 0x016f7c73,
		0x01e2bd8c, 0x01691947, 0x01b0cac7, 0x00d7e08b
	};
	
	public PerfectHash_Upper_G()
	{
		 super(state_upper_g,scramble_upper_g,value_upper_g,index_upper_g);
	}
}
