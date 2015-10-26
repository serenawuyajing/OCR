package edu.dal.mibio.corr.perfectHash;

public class PerfectHash_z extends PerfectHash{

	private static int[] state_z = {0x3c6ef372,0x3c6ef372,0x3c6ef372,0x3c6ef372,0x3c6ef372,0x3c6ef372,0x3c6ef372,0x3c6ef372};
	private static int value_z = 0x7fffff;
	private static int index_z = 53;
	private static int[] scramble_z = {
		0x00000000, 0x0001b924, 0x0008c3b4, 0x0008fa85,
		0x0001cfbc, 0x0006167d, 0x000d79e9, 0x00006b58,
		0x0005f9c6, 0x000906fa, 0x000178dc, 0x000bbf35,
		0x0003b0c8, 0x000941e9, 0x0004680c, 0x000ae201,
		0x0009c912, 0x000ed38d, 0x0001f82b, 0x00082192,
		0x000a4737, 0x000f0c73, 0x000309b0, 0x0002724c,
		0x000aea3a, 0x00063e99, 0x0004a032, 0x000b53b5,
		0x000c40eb, 0x0009d423, 0x00072d5d, 0x0006c737,
		0x000322a8, 0x0002d6f4, 0x00069873, 0x000e3dd6,
		0x000478a7, 0x00077e67, 0x0003b900, 0x000404b8,
		0x000a0a06, 0x00061bfa, 0x000a8d95, 0x00067923,
		0x0006b3a5, 0x000db142, 0x00056160, 0x0009f999,
		0x00031d8a, 0x000339e5, 0x0000904f, 0x00061846,
		0x000219aa, 0x000b3f23, 0x000e355b, 0x000cbdd5,
		0x000399a7, 0x000fe714, 0x00040f7b, 0x000e88ff,
		0x00092b0f, 0x0000dbef, 0x00018357, 0x00051f51,
		0x000e35ac, 0x000c09db, 0x00025ecb, 0x000b0ee1,
		0x000b356f, 0x000d41a5, 0x0008cb83, 0x000b0b87,
		0x000288e2, 0x0005e20f, 0x0002bdf2, 0x0006c41c,
		0x000e4c87, 0x000ac766, 0x000ae401, 0x0005686b,
		0x000abb43, 0x00063442, 0x00051df0, 0x0000cd6b,
		0x00019f0b, 0x0005526a, 0x0006eea9, 0x000e52de,
		0x000bd702, 0x0008bf58, 0x000f3053, 0x00006922,
		0x00080974, 0x00090ddf, 0x000fa42a, 0x000f4c08,
		0x00070245, 0x0006f106, 0x0000582e, 0x000f1f91,
		0x00049d70, 0x000d75e3, 0x000adc0e, 0x00092c3a,
		0x000c1da8, 0x000d2e51, 0x0006646f, 0x00061419,
		0x000465e9, 0x0009f5f6, 0x00026fac, 0x000dbde1,
		0x000a59f9, 0x0007c061, 0x000025ac, 0x000c3918,
		0x000287fc, 0x00084c4c, 0x0001eedc, 0x000dfda4,
		0x000e8734, 0x000d9991, 0x000a9f01, 0x00090270,
		0x000d73b2, 0x000fcfee, 0x000ff1d7, 0x0000a364,
		0x000231ce, 0x0009dadb, 0x000e613a, 0x0003588f,
		0x000277bb, 0x000940ab, 0x000701d7, 0x0000ff30,
		0x0004d37e, 0x000266c8, 0x000b447d, 0x000e2de0,
		0x00092fa2, 0x00031c26, 0x0009dca7, 0x0001229a,
		0x00067a2b, 0x000e2111, 0x000e02c6, 0x00076a40,
		0x0006d026, 0x000122d7, 0x000731c0, 0x000a2545,
		0x000bd0fc, 0x000280e4, 0x0004caf9, 0x000524e6,
		0x000ac79e, 0x0002d461, 0x000d2e82, 0x000ac5ff,
		0x000bc3bb, 0x00018bdd, 0x0007c374, 0x00049eeb,
		0x000be75e, 0x0003918e, 0x00090099, 0x000778aa,
		0x000cac70, 0x000a59b7, 0x000a04eb, 0x000d6e07,
		0x000bf625, 0x000f4f97, 0x000e450f, 0x0002d946,
		0x000a5e98, 0x00018a92, 0x0006929d, 0x000cc16e,
		0x0004ac14, 0x000a9b86, 0x00088933, 0x00028452,
		0x0009f654, 0x0001a8ca, 0x000f9032, 0x000cad4d,
		0x000d3def, 0x0005bddc, 0x0008cc77, 0x00081f15,
		0x000f0b7c, 0x000fb83a, 0x00007a11, 0x0001161f,
		0x0006a8c8, 0x000a3a0e, 0x0002963d, 0x000218c6,
		0x000a4799, 0x000ad3e7, 0x0000257f, 0x000707a3,
		0x00069172, 0x000deede, 0x00022542, 0x0005aaae,
		0x000b50d3, 0x000d7a00, 0x00057bd2, 0x0000fa90,
		0x000e6744, 0x000af0e6, 0x000d779f, 0x000ca310,
		0x0009250c, 0x000223c4, 0x00094a1c, 0x000b8862,
		0x0001ccce, 0x000817e2, 0x000240be, 0x000b2075,
		0x0004ac87, 0x000e85bc, 0x00094c93, 0x000283a8,
		0x00023afd, 0x0000a828, 0x000ecebb, 0x0007bd01,
		0x00044127, 0x000f0361, 0x00006ca6, 0x000a10cb,
		0x0003e15c, 0x0004762b, 0x000d19f9, 0x00076715,
		0x0004028d, 0x00065106, 0x000997d6, 0x0006759a,
		0x0007a75e, 0x000aa73b, 0x0001dc78, 0x000cef84,
		0x000e4537, 0x0001fe89, 0x0004def8, 0x000514fc,
		0x000e0607, 0x000abf1d, 0x000717a1, 0x0003a62d
	};
	
	public PerfectHash_z()
	{
		 super(state_z,scramble_z,value_z,index_z);
	}
}
