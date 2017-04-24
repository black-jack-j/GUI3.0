package gui;

import java.util.ArrayList;

import com.google.gson.Gson;

public class Parser {
	public static <T> T getFromJSon(String argument, Class T){
		Gson gs = new Gson();
		return (T)gs.fromJson(argument, T);
	}
	public static String[] getArgs(String s) {
		char[] chrs = s.toCharArray();
		String tmp;
		ArrayList<String> reply = new ArrayList<>();
		for (int i = 0;i<chrs.length;i++){
			tmp ="";
			while(i < chrs.length && (chrs[i]==' ' || chrs[i] == '\r' || chrs[i] == '\n')) i++;
			if (i >= chrs.length) break; 
			if (chrs[i] == '{'){
				tmp = tmp + chrs[i];
				int j = 1;
				while (j > 0){
					i++;
					if (chrs[i]=='{') j++; 
					else if (chrs[i] == '}') j--;
					else if (chrs[i] == '\r' || chrs[i] =='\n') continue;
					else if (chrs[i] == '\t') chrs[i] = ' ';
					tmp = tmp + chrs[i];
				}
				reply.add(tmp);
			}else{
				l:while(i < chrs.length){
					if (chrs[i] == '\r' || chrs[i] == ' ') break l;
					tmp = tmp + chrs[i];
					i++;
				}
				if (!tmp.isEmpty())reply.add(tmp);
			}
		}
		String[] replies = new String[reply.size()];
		replies = reply.toArray(replies);
		return replies;
	}
}

