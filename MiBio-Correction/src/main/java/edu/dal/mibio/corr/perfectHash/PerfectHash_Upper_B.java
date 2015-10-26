package edu.dal.mibio.corr.perfectHash;

public class PerfectHash_Upper_B extends PerfectHash{

	private static int[] state_upper_b = {0x9e3779b9,0x9e3779b9,0x9e3779b9,0x9e3779b9,0x9e3779b9,0x9e3779b9,0x9e3779b9,0x9e3779b9};
	private static int value_upper_b = 0x1ffffff;
	private static int index_upper_b = 3;
	private static int[] scramble_upper_b = {
		0x00000000, 0x01fc28ef, 0x01189999, 0x028ecfc3,
		0x0376034d, 0x002657e3, 0x026d8e74, 0x0089bde8,
		0x025fee9a, 0x01ea0d17, 0x03a2b6f2, 0x0194bea1,
		0x035ef06f, 0x0370dfb9, 0x012ff613, 0x03306fee,
		0x036bcf52, 0x00eed913, 0x00940c84, 0x00de0975,
		0x0184086a, 0x022393e6, 0x025fd2f2, 0x00a29d5b,
		0x0251fbd0, 0x02a3cb00, 0x03e382e2, 0x01009010,
		0x026581c0, 0x00d3e956, 0x01812b2c, 0x00fcb907,
		0x02763d64, 0x014db793, 0x02ae3c66, 0x02920d8c,
		0x038f7249, 0x035d2475, 0x02f3f6db, 0x0302a90b,
		0x02972148, 0x02e920e6, 0x01eab34d, 0x017c59b5,
		0x0055bb1b, 0x0365bad0, 0x00b44d52, 0x031472d2,
		0x01f16e47, 0x00748342, 0x0214faa9, 0x031ccafc,
		0x03a8d25c, 0x032e9b2a, 0x03bc9f31, 0x01921684,
		0x02766538, 0x0362f37d, 0x0035e519, 0x007b8b50,
		0x004f2962, 0x01eef485, 0x02d8a226, 0x0263787d,
		0x02737a39, 0x0059d373, 0x02b6b7c7, 0x02cfa74b,
		0x0207f841, 0x02610eb5, 0x02b207ab, 0x00bfe64a,
		0x01cc2513, 0x01cec092, 0x009b679d, 0x0349e78b,
		0x016334c3, 0x0163938f, 0x03e8d39d, 0x01b4f84a,
		0x02940097, 0x0092facf, 0x00beea7a, 0x02a89a7c,
		0x034fd439, 0x0290e40b, 0x0120bd38, 0x03d39c2b,
		0x01e0adf4, 0x005aaf89, 0x024e9dd1, 0x0110da9f,
		0x0105fb52, 0x0240af30, 0x03b6f1f9, 0x000085d0,
		0x00b5db38, 0x02abf886, 0x01989412, 0x01218036,
		0x002741bc, 0x018eb98d, 0x03f03d0c, 0x0043a8c3,
		0x00f8b150, 0x00ae3829, 0x002363ce, 0x00e82a0b,
		0x01bd8298, 0x004a4329, 0x002ccdfe, 0x007d2c56,
		0x01b45822, 0x0354b3ff, 0x007cd7e6, 0x03531f4e,
		0x029b9680, 0x01efecd1, 0x00637749, 0x014d241e,
		0x02defd62, 0x01e6754b, 0x007cb264, 0x02ed1bed,
		0x00736389, 0x02f01789, 0x0026f007, 0x019ec131,
		0x0153956b, 0x00ed11f9, 0x0372914b, 0x0112eae6,
		0x038f3cbb, 0x035e9613, 0x0180a644, 0x0302ffd9,
		0x01d34df2, 0x03523716, 0x02fcb86a, 0x036bccd5,
		0x001a397f, 0x01f10708, 0x0151569a, 0x02fe0613,
		0x02ed35f5, 0x000b1686, 0x00d7c227, 0x0082afba,
		0x00dd050f, 0x03ce9657, 0x009f5ea5, 0x0372df57,
		0x0269eab7, 0x03ab1728, 0x00253789, 0x00c69b0f,
		0x00a6da9a, 0x00b74569, 0x03d56066, 0x027f1ed9,
		0x013d59c8, 0x03b45907, 0x02a10f85, 0x0142e38c,
		0x01814fcb, 0x00ab6584, 0x008e5450, 0x00d845b1,
		0x03a14b40, 0x03e0dd9c, 0x008be33f, 0x01b2ed36,
		0x02bc79c8, 0x02172e03, 0x026d7ab7, 0x02370083,
		0x01ad3336, 0x00bbefe5, 0x021c0e2c, 0x027eba76,
		0x024b9a62, 0x014bdf69, 0x0046dd03, 0x0250ae3f,
		0x0169fb72, 0x0255327d, 0x01bb86db, 0x031e0a56,
		0x03250e8e, 0x02278df9, 0x02d9cf3d, 0x0008841b,
		0x0047f38e, 0x02644977, 0x0064ec20, 0x0095a787,
		0x00d17b97, 0x020f0664, 0x016c2752, 0x020efce6,
		0x029e2139, 0x028cfc15, 0x00263934, 0x022169d1,
		0x0132cca7, 0x02005db4, 0x019df180, 0x02eec0a9,
		0x004a4a99, 0x00571e91, 0x037a5feb, 0x037640e7,
		0x0091beb2, 0x01d1dd9a, 0x00535adb, 0x01eaab0e,
		0x002b59e7, 0x0220d439, 0x00c441eb, 0x00a72580,
		0x01917816, 0x034ca39d, 0x0025ffe9, 0x03987763,
		0x01a5c241, 0x0129cfd6, 0x0180c1e8, 0x01106707,
		0x01028caa, 0x01ee2f86, 0x020fe9a4, 0x0340354e,
		0x030bf003, 0x01e48b6e, 0x01f6c228, 0x00bb3976,
		0x0304c6c1, 0x02953770, 0x02fe5053, 0x01d43ed9,
		0x031156b3, 0x0294d6bd, 0x001496de, 0x01509b49,
		0x00988e31, 0x02ac1fc2, 0x037985ca, 0x00bb5bc5,
		0x0122770c, 0x028675c3, 0x01038c6e, 0x03e42754,
		0x0320cd8a, 0x02688861, 0x02043784, 0x000a9427
	};
	
	public PerfectHash_Upper_B()
	{
		 super(state_upper_b,scramble_upper_b,value_upper_b,index_upper_b);
	}
}
