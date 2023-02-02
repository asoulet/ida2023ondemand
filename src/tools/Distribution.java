package tools;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map.Entry;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Distribution implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Integer> values = new ArrayList<Integer>();
	private ArrayList<Integer> counts = new ArrayList<Integer>();
	private String name;
	
	public Distribution(String filename, String name) {
		this.name = name;
		Reader in;
		try {
			in = new FileReader(filename);
			Iterable<CSVRecord> records = CSVFormat.newFormat(';').withHeader().parse(in);
			for (CSVRecord record : records) {
			    int k = Integer.parseInt(record.get("k"));
			    int count = Integer.parseInt(record.get("count"));
			    add(k, count);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Distribution(ArrayList<Integer> sample, String name) {
		this.name = name;
		Collections.sort(sample);
		int previous = -1;
		int count = 0;
		for (Integer value : sample) {
			if (value != previous) {
				if (previous != -1) {
					values.add(previous);
					counts.add(count);
					count = 0;
				}
				previous = value;					
			}
			count++;
		}
		if (previous != -1) {
			values.add(previous);
			counts.add(count);
			count = 0;
		}
	}
	
	public void show() {
		for (int i = 0; i < values.size(); i++)
			System.out.println(values.get(i) + "\t" + counts.get(i));
	}

	public void add(int value, int count) {
		values.add(value);
		counts.add(count);
	}

	public int getMaximumDegree() {
		if (values.size() == 0)
			return 0;
		return values.get(values.size() - 1);
	}
	
	public int size() {
		return values.size();
	}
	
	public int getValue(int i) {
		return values.get(i);
	}
	
	public int getCount(int i) {
		return counts.get(i);
	}
	
	public static void display(ArrayList<Distribution> distributions) {
		int max = 0;
		for (Distribution d : distributions)
			max = Math.max(max, d.size());
		for (int i = 0; i < max; i++) {
			for (Distribution d : distributions)
				if (d.size() > i)
					System.out.print(d.getValue(i) + "\t" + d.getCount(i) + "\t");
				else
					System.out.print("\t\t");		
			System.out.println();
		}
	}
	
	public int getCount() {
		int count = 0;
		for (int i = 0; i < values.size(); i++)
			count += counts.get(i);
		return count;
	}
	
	public int getCumulative() {
		int count = 0;
		for (int i = 0; i < values.size(); i++)
			count += counts.get(i) * values.get(i);
		return count;
	}
	
	public double [] getSample() {
		double [] sample = new double [getCount()];
		int i = 0;
		for (int j = 0; j < values.size(); j++) {
			for (int k = 0; k < counts.get(j); k++) {
				sample[i++] = values.get(j);
			}
		}
		return sample;
	}

	@Override
	public String toString() {
		return name;
	}
	
	
}
