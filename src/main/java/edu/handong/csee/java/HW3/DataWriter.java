package edu.handong.csee.java.HW3;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.io.IOException;

public class DataWriter {
	public void writeAFile(ArrayList<String> lines, String targetFileName) {
		try {
			File files = new File(targetFileName);
			FileOutputStream fos = new FileOutputStream(files);
			DataOutputStream dos = new DataOutputStream(fos);
			
			for(String line:lines) {
				dos.write((line+"\n").getBytes());
			}
			dos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}