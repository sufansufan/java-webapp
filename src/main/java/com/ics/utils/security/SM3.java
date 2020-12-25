package com.ics.utils.security;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

import com.ics.utils.ConstantProperty;
import com.ics.utils.StringUtil;

/**
 * 国密SM3算法，消息摘要
 * String sSM3 = SM3.hash("123456");
 * @author zhangqiushui
 *
 */
public final class SM3 
{
	private static char[] m_hexChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	private static final BigInteger m_biIV = new BigInteger("7380166f4914b2b9172442d7da8a0600a96f30bc163138aae38dee4db0fb0e4e", 16);
	private static final Integer m_iTj15 = Integer.valueOf("79cc4519", 16);
	private static final Integer m_iTj63 = Integer.valueOf("7a879d8a", 16);
	private static final byte[] m_byFirstPadding = {(byte)0x80};
	private static final byte[] m_byZeroPadding = {(byte)0x00};

	public static int T(int j) 
	{
		if (j >= 0 && j <= 15) 
		{
			return m_iTj15.intValue();
		} 
		else if (j >= 16 && j <= 63)
		{
			return m_iTj63.intValue();
		}
		else
		{
			return 0;
		}
	}

	public static int FF(Integer x, Integer y, Integer z, int j)
	{
		if (j >= 0 && j <= 15) 
		{
			return Integer.valueOf(x.intValue() ^ 
					y.intValue() ^ 
					z.intValue());
		}
		else if (j >= 16 && j <= 63) 
		{
			return Integer.valueOf((x.intValue() & y.intValue())
					| (x.intValue() & z.intValue())
					| (y.intValue() & z.intValue()));
		} 
		else
		{
			return 0;
		}
	}

	public static int GG(Integer x, Integer y, Integer z, int j)
	{
		if (j >= 0 && j <= 15) 
		{
			return Integer.valueOf(x.intValue() ^ 
					y.intValue() ^ 
					z.intValue());
		}
		else if (j >= 16 && j <= 63)
		{
			return Integer.valueOf((x.intValue() & y.intValue()) | 
					(~x.intValue() & z.intValue()));
		}
		else 
		{
			return 0;
		}
	}

	public static Integer P0(Integer x)
	{
		return Integer.valueOf(x.intValue()
				^ Integer.rotateLeft(x.intValue(), 9)
				^ Integer.rotateLeft(x.intValue(), 17));
	}

	public static Integer P1(Integer x) 
	{
		return Integer.valueOf(x.intValue() ^ 
				Integer.rotateLeft(x.intValue(), 15) ^ 
				Integer.rotateLeft(x.intValue(), 23));
	}

	public static byte[] padding(byte[] source) 
	{
		if (source.length >= 0x2000000000000000l) 
		{
			return null;
		}
		long l = source.length * 8;
		long k = 448 - (l + 1) % 512;
		if (k < 0) 
		{
			k = k + 512;
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try 
		{
			baos.write(source);
			baos.write(m_byFirstPadding);
			long i = k - 7;
			while (i > 0)
			{
				baos.write(m_byZeroPadding);
				i -= 8;
			}
			baos.write(long2Bytes(l));
		} 
		catch (IOException e) 
		{
			return null;
		}
		
		return baos.toByteArray();
	}

	public static byte[] long2Bytes(long l) 
	{
		byte[] bytes = new byte[8];
		for (int i = 0; i < 8; i++)
		{
			bytes[i] = (byte) (l >>> ((7 - i) * 8));
		}
		return bytes;
	}

	private static byte[] hashOriginal(byte[] source)
	{
		byte[] m1 = padding(source);
		int n = m1.length / (512 / 8);
		byte[] b = null;
		byte[] vi = m_biIV.toByteArray();
		byte[] vi1 = null;
		for (int i = 0; i < n; i++) 
		{
			b = Arrays.copyOfRange(m1, i * 64, (i + 1) * 64);
			vi1 = CF(vi, b);
			vi = vi1;
		}
		return vi1;
	}

	private static byte[] CF(byte[] vi, byte[] bi) 
	{
		int a, b, c, d, e, f, g, h;
		a = toInteger(vi, 0);
		b = toInteger(vi, 1);
		c = toInteger(vi, 2);
		d = toInteger(vi, 3);
		e = toInteger(vi, 4);
		f = toInteger(vi, 5);
		g = toInteger(vi, 6);
		h = toInteger(vi, 7);

		int[] w = new int[68];
		int[] w1 = new int[64];
		for (int i = 0; i < 16; i++)
		{
			w[i] = toInteger(bi, i);
		}
		for (int j = 16; j < 68; j++)
		{
			w[j] = P1(w[j - 16] ^ w[j - 9] ^ Integer.rotateLeft(w[j - 3], 15))
					^ Integer.rotateLeft(w[j - 13], 7) ^ w[j - 6];
		}
		for (int j = 0; j < 64; j++)
		{
			w1[j] = w[j] ^ w[j + 4];
		}
		
		int ss1, ss2, tt1, tt2;
		for (int j = 0; j < 64; j++)
		{
			ss1 = Integer.rotateLeft(Integer.rotateLeft(a, 12) + 
					e + 
					Integer.rotateLeft(T(j), j), 7);
			ss2 = ss1 ^ Integer.rotateLeft(a, 12);
			tt1 = FF(a, b, c, j) + d + ss2 + w1[j];
			tt2 = GG(e, f, g, j) + h + ss1 + w[j];
			d = c;
			c = Integer.rotateLeft(b, 9);
			b = a;
			a = tt1;
			h = g;
			g = Integer.rotateLeft(f, 19);
			f = e;
			e = P0(tt2);
		}
		byte[] v = toByteArray(a, b, c, d, e, f, g, h);
		for (int i = 0; i < v.length; i++)
		{
			v[i] = (byte) (v[i] ^ vi[i]);
		}
		return v;
	}

	public static int toInteger(byte[] source, int index) 
	{
		StringBuilder sbVal = new StringBuilder("");
		for (int i = 0; i < 4; i++) 
		{
			sbVal.append(m_hexChar[(byte) ((source[index * 4 + i] & 0xF0) >> 4)]);
			sbVal.append(m_hexChar[(byte) (source[index * 4 + i] & 0x0F)]);
		}
		return Long.valueOf(sbVal.toString(), 16).intValue();
	}

	public static byte[] toByteArray(int a, int b, int c, int d, int e, int f, int g, int h)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream(32);
		try 
		{
			baos.write(toByteArray(a));
			baos.write(toByteArray(b));
			baos.write(toByteArray(c));
			baos.write(toByteArray(d));
			baos.write(toByteArray(e));
			baos.write(toByteArray(f));
			baos.write(toByteArray(g));
			baos.write(toByteArray(h));
		} 
		catch (IOException ex) 
		{
			return null;
		}
		
		return baos.toByteArray();
	}

	public static byte[] toByteArray(int i)
	{
		byte[] byteArray = new byte[4];
		byteArray[0] = (byte) (i >>> 24);
		byteArray[1] = (byte) ((i & 0xFFFFFF) >>> 16);
		byteArray[2] = (byte) ((i & 0xFFFF) >>> 8);
		byteArray[3] = (byte) (i & 0xFF);
		return byteArray;
	}

	public static String hash(String _sOriginal)
	{
		byte[] abyOriginal = StringUtil.string2ByteArray(_sOriginal);
		byte[] abyHash = hashOriginal(abyOriginal);
		String sHash = StringUtil.byteArray2StringHex(abyHash);

		return sHash;
	}
	
	public static void main(String[] args) {
		System.out.println(SM3.hash("doukemeng123" + ConstantProperty.userSalt));
	}
}
