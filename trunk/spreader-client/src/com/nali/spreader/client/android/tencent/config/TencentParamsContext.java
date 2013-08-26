package com.nali.spreader.client.android.tencent.config;

import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

import com.nali.spreader.client.android.tencent.utils.MacAddress;
import com.nali.spreader.client.android.tencent.utils.Phone;

public class TencentParamsContext {
	private static final ThreadLocal<TencentParamsContext> ctx = new ThreadLocal<TencentParamsContext>();
	private static final Random androidRandom = new Random();
	private long time;
	private int guid = 0;
	private String machineUniqueId = genMachineUniqueId();
	private String imsi = genImsi();
	private int requestId = RandomUtils.nextInt(99);
	private String phoneName = Phone.get();
	private int dpi;
	private int resX;
	private int resY;
	private String androidVersion = "Android4.1.2";
	private String macAddres = MacAddress.randomLowerCaseMacAddress(null);

	public TencentParamsContext() {
		super();
	}

	public TencentParamsContext(String phoneName, String androidVersion,
			String imsi, int dpi, int x, int y) {
		super();
		this.phoneName = phoneName;
		this.androidVersion = androidVersion;
		this.imsi = imsi;
		this.dpi = dpi;
		this.resX = x;
		this.resY = y;
	}

	public TencentParamsContext(String machineUniqueId, int requestId,
			String phoneName, int guid, long time, String macAddres,
			String imsi, String androidVersion, int dpi, int x, int y) {
		super();
		if (StringUtils.isNotBlank(machineUniqueId)) {
			this.machineUniqueId = machineUniqueId;
		}
		if (requestId > 0) {
			this.requestId = requestId;
		}
		if (StringUtils.isNotBlank(phoneName)) {
			this.phoneName = phoneName;
		}
		if (StringUtils.isNotBlank(macAddres)) {
			this.macAddres = macAddres;
		}
		if (StringUtils.isNotBlank(imsi)) {
			this.imsi = imsi;
		}
		if (StringUtils.isNotBlank(androidVersion)) {
			this.androidVersion = androidVersion;
		}
		this.guid = guid;
		this.time = time;
		this.dpi = dpi;
		this.resX = x;
		this.resY = y;
	}

	private static String genMachineUniqueId() {
		long id = 10000 + androidRandom.nextInt(90000);
		for (int i = 0; i < 2; i++) {
			id += androidRandom.nextInt(100000) + id * 100000L;
		}
		return String.valueOf(id);
	}

	private static String genImsi() {
		StringBuilder imsi = new StringBuilder("460");
		String[] mncs = { "00", "01", "02", "03", "05", "06", "07" };
		String mnc = mncs[RandomUtils.nextInt(7)];
		imsi.append(mnc);
		imsi.append(RandomStringUtils.randomNumeric(10));
		return imsi.toString();
	}

	public void setMachineUniqueId(String machineUniqueId) {
		this.machineUniqueId = machineUniqueId;
	}

	public static TencentParamsContext getCurrentContext() {
		return ctx.get();
	}

	public static void setTencentParamsContext(TencentParamsContext t) {
		ctx.set(t);
	}

	public static void remove() {
		ctx.remove();
	}

	public int getGuid() {
		return guid;
	}

	public void setGuid(int guid) {
		this.guid = guid;
	}

	public int getRequestId() {
		return requestId++;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public long getTime() {
		return time;
	}

	public void timeGo(long used) {
		time += used;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getMachineUniqueId() {
		return machineUniqueId;
	}

	public String getPhoneName() {
		return phoneName;
	}

	public void setPhoneName(String phoneName) {
		this.phoneName = phoneName;
	}

	public String getMacAddres() {
		return macAddres;
	}

	public void setMacAddres(String macAddres) {
		this.macAddres = macAddres;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getAndroidVersion() {
		return androidVersion;
	}

	public void setAndroidVersion(String androidVersion) {
		this.androidVersion = androidVersion;
	}

	public int getDpi() {
		return dpi;
	}

	public void setDpi(int dpi) {
		this.dpi = dpi;
	}

	public int getResX() {
		return resX;
	}

	public void setResX(int resX) {
		this.resX = resX;
	}

	public int getResY() {
		return resY;
	}

	public void setResY(int resY) {
		this.resY = resY;
	}
}
