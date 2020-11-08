import java.io.*;
import java.util.*;

public class Lab5 {
	
	/* In this program I create an array of Scanners, 4 scanners to be exists, each Scanner reads a file which holds a csv file from spotifycharts.com,
	 * 4 scanners for 4 files, each file is the top 200 list for a week of September, making up the entire month of September.
	 * A for loop goes through the array of scanners while a while loop inside the for loop reads each file, it extracts the name, artist and number of
	 * plays for each song and adds each to an arraylist for the respective data, then the duplicates are removed from the arraylists and the arraylists
	 * are sorted in ascending alphabetical order. 
	 * A for loop computes the avergae number of plays for a song over the month of September.
	 * Lastly an object of my custom tree class is instantiated, all the songs, artists and average number of streams for the song are inserted into the 
	 * Tree object to make a playlist, then the contents of the playlist are printed to the console. */

	public static void main(String[] args) throws Exception {
		
		
		//Array of 4 scanner, for 4 files for 4 weeks of September
		Scanner[] SeptemberCharts = { 
				new Scanner(new File("regional-us-weekly-2020-09-04--2020-09-11.csv"), "UTF-8"),
				new Scanner(new File("regional-us-weekly-2020-09-11--2020-09-18.csv"), "UTF-8"),
				new Scanner(new File("regional-us-weekly-2020-09-18--2020-09-25.csv"), "UTF-8"),
				new Scanner(new File("regional-us-weekly-2020-09-25--2020-10-02.csv"), "UTF-8"),
		};
		
		//3 Arraylists for songs, artists and number of streams
		ArrayList<String> artists = new ArrayList<>(800);
		ArrayList<String> songs = new ArrayList<>(800);
		ArrayList<String> streams = new ArrayList<>(800);
		
		
		//this for loop goes through the array of scanner
		for (int i = 0; i < SeptemberCharts.length; i++) {
			//these two statements skip the first 2 lines of the csv files which are irrelavant header lines
			String header = SeptemberCharts[i].nextLine();
			header = SeptemberCharts[i].nextLine();
			
			String[] line = null;
			
			//this while loop reads each file
			while (SeptemberCharts[i].hasNext()) {
				line = SeptemberCharts[i].nextLine().split(","); //splits a line by commas
			
				/* If the length of the array is 5 then the index 1 is the song name, index 2 is the artist and index 3 is the
				 * number of plays*/
				
				if (line.length == 5) {
					
					//these if statements remove quptation marks from song and artist names
					if (line[1].charAt(0) == '"') 
						line[1] = line[1].substring(1, line[1].length()-1);
					
					if (line[2].charAt(0) == '"') 
						line[2] = line[2].substring(1, line[2].length()-1);
						
					songs.add(line[1]);
					artists.add(line[2]);
					streams.add(line[3]);
				} //end of if
				
				//if the array is not length 5 then the line has a song which has a comma in the name, the handles that situation
				
				else {
					String song = "";
					
					 /* this for makes sure to get the song name which is the 2nd value of every row, and in the case that the 
					  * second value itself has commas, then the for loop gets all the values that would belong to the 2nd row,
					  * and the loop stops when it reaches the 3rd row which contains the artist */
					
					for (int j = 1; j < line.length-3; j++)
							song += line[j];
					
					//these if statements remove quptation marks from song and artist names
					if (song.charAt(0) == '"') 
						song = song.substring(1, song.length()-1);
					
					if (line[line.length-3].charAt(0) == '"') 
						line[line.length-3] = line[line.length-3].substring(1, line[line.length-3].length()-1);
							
					songs.add(song);
					artists.add(line[line.length-3]);
					streams.add(line[line.length-2]);
				} //end of else 
			} // end of while
		} //end of for
		
		
		//this for loop removes duplicates
		for (int i = 0; i < songs.size(); i++) 
			for (int j = i+1; j < songs.size()-1; j++) 
				if (songs.get(i).equals(songs.get(j))) {
					artists.remove(j);
					songs.remove(j);
				
					/* if a duplicate is found then the number of streams will be needed for the purpose of finding the average,
					 * this statement here concats a song's number of plays from different weeks into one big string, each number
					 * is separated by a comma, this is so this string can later be split to compute the average */
					String plays = streams.get(i) + "," + streams.get(j);

					streams.set(i, plays);
					streams.remove(j);
					j--;
				} //end of inner for loop
			
		
		/*This for loop computes the average number of plays per month, it first checks if a string has a comma in the first place
		 * if it doesn't then an if statement returns false, and it skips that number, meaning that song only appeared once in the 
		 * top 200, so its number of plays is also the average for that month. Otherwise it splits the string by commas, adds the 
		 * values to an array, converts them to an int, computes the average, then converts it back to a string and replaces the 
		 * original string it took with commas back into the arraylist */
		for (int i = 0; i < streams.size(); i++) {
			int plays = 0;
			int cnt = 0;
			boolean comma = false;
			
			for (int j = 0; j < streams.get(i).length(); j++)
				if (streams.get(i).charAt(j) == ',')
					comma = true;
			
			if (comma == true) {
				String[] sumPlays = streams.get(i).split(",");
				for (int k = 0; k < sumPlays.length; k++) {
					int[] intPlays = new int[sumPlays.length];
					intPlays[k] = Integer.parseInt(sumPlays[k]);
					plays += intPlays[k];
					cnt++;
				}
			} //end of if
			
			if (plays != 0) {
				int avg = plays/cnt;
				String avgPlays = Integer.toString(avg);
				streams.set(i, avgPlays);
			} //end of if
		} //end of for 
	
		//bubble sort to sort the songs in alphabetical order, i didnt use merge or quick sort so that my code is not too complicated, and
		//the arraylist is only 263 elements anyways, so for this specific project its not very inefficient 
		//since the artist and streams arraylist and indices correspond with the songs arraylist, whenever 2 song elements are swapped, so 
		//are the corresponding artist and stream elements
		for (int i = 0; i < songs.size(); i++) {
			String temp = "";
			for (int j = i+1; j < songs.size(); j++) {
				if (songs.get(i).toLowerCase().charAt(0) > (songs.get(j).toLowerCase()).charAt(0)) {
					temp = artists.get(i);
					artists.set(i, artists.get(j));
					artists.set(j, temp);
					
					temp = songs.get(i);
					songs.set(i, songs.get(j));
					songs.set(j, temp);
					
					temp = streams.get(i);
					streams.set(i, streams.get(j));
					streams.set(j, temp);
				}
			}
		}
		
		//creating an instance of my custom binary search tree class, since my arraylist is already sorted its perfect for a BST anyways
		SongTree playlist = new SongTree();
		
		//I use a for loop to go through the arraylist and use my BST's insert method to add a new node, the node's data fields are song,
		//artist and average number of streams
		for (int i = 0; i < songs.size(); i++) 
			playlist.insert(songs.get(i), artists.get(i), streams.get(i));
		
		PrintWriter output = new PrintWriter("ArtistsSorted-Monthof092020.txt");
		//finally I call my BST's displayTree method which uses inorder traversal to print the entire BST in ascending order.
		playlist.displayTree(output);
		
		SeptemberCharts[0].close();
		SeptemberCharts[1].close();
		SeptemberCharts[2].close();
		SeptemberCharts[3].close();
		output.close();
	}
}
