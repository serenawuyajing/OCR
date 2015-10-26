package edu.dal.mibio.corr.perfectHash;

public class PerfectHash_Upper_S extends PerfectHash{
	
	private static int[] state_upper_s = {0x78dde6e4,0x78dde6e4,0x78dde6e4,0x78dde6e4,0x78dde6e4,0x78dde6e4,0x78dde6e4,0x78dde6e4};
	private static int value_upper_s = 0x3ffffff;
	private static int index_upper_s = 20;
	private static int[] scramble_upper_s = {
		0x00000000, 0x01ea190a, 0x0570358e, 0x06bc9136,
		0x0566ac19, 0x05928895, 0x079c1d74, 0x00809fa5,
		0x07ff6b55, 0x05dbc8dc, 0x00d9e5be, 0x025db976,
		0x010a933e, 0x05a95844, 0x01a7915d, 0x00dc7cab,
		0x01ef0788, 0x0524f641, 0x06e2bd8f, 0x05832c3e,
		0x04b2b75c, 0x00ae7062, 0x060db545, 0x053774b9,
		0x01c1c04c, 0x01c187d6, 0x06cd7cee, 0x0767f367,
		0x050f05b9, 0x073e24f1, 0x001fda71, 0x02ea18d8,
		0x062fdbd6, 0x00d1edf5, 0x02c8156b, 0x06b4f163,
		0x0731403a, 0x04c2c7fa, 0x022db74f, 0x0362d11d,
		0x005f324b, 0x003acb4c, 0x00744ce0, 0x07cac84c,
		0x07cdfc3d, 0x0713fb43, 0x026345a0, 0x05f24c61,
		0x0069bf73, 0x05c8aaec, 0x07a32b57, 0x0748f4fa,
		0x054957ba, 0x01ef652e, 0x04e1ee3f, 0x03eecf7a,
		0x05527dd2, 0x04673673, 0x058f6e99, 0x056f0ca4,
		0x00c9a0c5, 0x07b2a475, 0x01aae226, 0x047b0798,
		0x07f85122, 0x036d154f, 0x01f11069, 0x02826520,
		0x0640b789, 0x008efca3, 0x027b8630, 0x03db72c7,
		0x016cb214, 0x00eb8f6f, 0x05856f94, 0x02b15f27,
		0x00d9b80a, 0x03a3215c, 0x072fe409, 0x013cb581,
		0x05ef937d, 0x0330019d, 0x05375682, 0x0703bda0,
		0x07cd3302, 0x049bd933, 0x054f879e, 0x07573ce5,
		0x072d6033, 0x039bd290, 0x04f6b8ff, 0x014ba93c,
		0x03d57874, 0x017c233b, 0x0149ee3e, 0x0429e363,
		0x07850c7f, 0x0160babf, 0x042e0d05, 0x07048ac7,
		0x06379a58, 0x0344d08a, 0x0183e400, 0x0274a6c3,
		0x036b4aa5, 0x0332e313, 0x03f7bb0e, 0x026cbd02,
		0x06366ee6, 0x04e8cb40, 0x07d15c12, 0x055ae8bb,
		0x07c0170e, 0x04ea92f6, 0x07895a2e, 0x01da47e0,
		0x06793a10, 0x01f6ccf9, 0x00b16978, 0x010e8b80,
		0x005163c0, 0x0535691a, 0x067b3e70, 0x06d38054,
		0x00d3f7c3, 0x04c7f65e, 0x027deabc, 0x05965206,
		0x02eb5783, 0x029ffc3b, 0x05ef5d0d, 0x00c9bb56,
		0x063d6ef5, 0x03865cf2, 0x0650ec5d, 0x065c7988,
		0x028ee35c, 0x01336dc7, 0x0457d170, 0x023691e4,
		0x0789dd62, 0x06f5abae, 0x07880e63, 0x0070083e,
		0x019c59fc, 0x004e9910, 0x031462f1, 0x03afcdd3,
		0x0273c1ab, 0x04b164fd, 0x036f0fd6, 0x007bb1f6,
		0x04447132, 0x04cf4a6d, 0x018f90db, 0x00bc24b2,
		0x06832f7b, 0x02b1e348, 0x0112ca39, 0x0145f91a,
		0x0355cccb, 0x0461d915, 0x00ec8d90, 0x0503e165,
		0x033c6892, 0x04ee916d, 0x05a286b5, 0x056165bb,
		0x063b3a2c, 0x01cedd94, 0x041ec813, 0x03d39b88,
		0x00f2e415, 0x01919c51, 0x02c033cc, 0x0239de80,
		0x0306c1ff, 0x06ec9e06, 0x06eed399, 0x07e1935b,
		0x006eee1f, 0x0339ffcd, 0x07aec3d9, 0x04057960,
		0x06f55d8f, 0x07f09bdd, 0x00a6e653, 0x055071c6,
		0x07181348, 0x03b703fd, 0x07c75a53, 0x04eb84e2,
		0x050f2786, 0x062c1186, 0x064db487, 0x00b8bb90,
		0x04149163, 0x0665262d, 0x078c0d93, 0x045cd6dc,
		0x064c7317, 0x0449b55f, 0x04964574, 0x0026bc3d,
		0x04029a76, 0x072b8974, 0x029e2f4a, 0x074f5fdd,
		0x073eb335, 0x051c07a3, 0x04c7cfa7, 0x0272bd48,
		0x06487f27, 0x0589b4b0, 0x01d3c416, 0x05a94f66,
		0x00812a35, 0x0335d5b2, 0x05f5aaef, 0x05a82cae,
		0x0756f763, 0x040b2d71, 0x07f14487, 0x053373db,
		0x033c8c25, 0x019fd8a9, 0x0086a35c, 0x04d757a6,
		0x075dd2ed, 0x0182874f, 0x007cbcca, 0x0377dbaa,
		0x0515b1f3, 0x03566b82, 0x07c5b489, 0x078fa1fb,
		0x069d2e96, 0x05ebd887, 0x03d310a6, 0x05fa5d96,
		0x054ace16, 0x04ac9880, 0x07fb2b6d, 0x01cb8853,
		0x0660e6c9, 0x0466452d, 0x01fbb1ec, 0x0217a818,
		0x04c678be, 0x0719a627, 0x07abba2a, 0x05ea25fb,
		0x077632d8, 0x07dc4cb6, 0x0172609a, 0x026227e0
	};
	
	public PerfectHash_Upper_S()
	{
		 super(state_upper_s,scramble_upper_s,value_upper_s,index_upper_s);
	}
}
