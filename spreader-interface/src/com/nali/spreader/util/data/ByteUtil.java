package com.nali.spreader.util.data;

public class ByteUtil {
	public static int makeInt(byte b3, byte b2, byte b1, byte b0) {
		return (int) ((((b3 & 0xff) << 24) | ((b2 & 0xff) << 16) | ((b1 & 0xff) << 8) | ((b0 & 0xff) << 0)));
	}

	public static long makeLong(byte b7, byte b6, byte b5, byte b4, byte b3, byte b2, byte b1, byte b0) {
		return ((((long) b7 & 0xff) << 56) | (((long) b6 & 0xff) << 48) | (((long) b5 & 0xff) << 40)
				| (((long) b4 & 0xff) << 32) | (((long) b3 & 0xff) << 24) | (((long) b2 & 0xff) << 16)
				| (((long) b1 & 0xff) << 8) | (((long) b0 & 0xff) << 0));
	}
	
	public static int makeInt(byte[] bs, int offset) {
		return makeInt(bs[offset], bs[offset+1], bs[offset+2], bs[offset+3]);
	}
	
	public static long makeLong(byte[] bs, int offset) {
		return makeLong(
				bs[offset], bs[offset+1], bs[offset+2], bs[offset+3],
				bs[offset+4], bs[offset+5], bs[offset+6], bs[offset+7]);
	}
}
