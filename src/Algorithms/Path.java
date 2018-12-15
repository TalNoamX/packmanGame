package Algorithms;

import java.util.ArrayList;

import Coords.MyCoords;
import GameData.Fruit;
import GameData.Pacman;

public class Path extends ArrayList<PathNode> {
	private static final long serialVersionUID = 1L;
	MyCoords coords = new MyCoords();
	
	public Path() {}

	public Path(ArrayList<Fruit> fList,Pacman pman) {
		ArrayList<Fruit> sortedFL = sortByDist(fList,pman);
		double runTime;
		for(int i=0; i<sortedFL.size();i++) {
			if (i==0) {
				runTime=(coords.distance3d(pman.getPoint(), sortedFL.get(i).getPoint())/pman.getSpeed());
				this.add(new PathNode(pman.getID(),sortedFL.get(i).getID(),runTime));
			}
			else {
				runTime=(coords.distance3d(sortedFL.get(i-1).getPoint(), sortedFL.get(i).getPoint())/pman.getSpeed());
				this.add(new PathNode(sortedFL.get(i-1).getID(),sortedFL.get(i).getID(),runTime));
			}
		}
	} 
	/**
	 * 
	 * @param fList the original fruit list
	 * @param pman the pacman we want to check path for him.
	 * @return the same fruit list sorted by the shortest path the pacman need to run to eat them all.
	 */

	private ArrayList<Fruit> sortByDist(ArrayList<Fruit>fList,Pacman pman) {
		
		ArrayList<Fruit> copyFList = new ArrayList<Fruit>(fList); //make a deep copy of the fruit list
		ArrayList<Fruit> sortedFL = new ArrayList<>(); // create a new list that will be sorted by distance from from the pacman.
		
		for(int i=0;i<fList.size();i++) { // loop that run on every element in the original list
			double shortestDist=0; // keep the shortest distance between the Points
			int rememberIndex=0; // keep the index of the most close point.
			
			for(int j=0;j<copyFList.size();j++) { // run on the copy list because her elements are getting delete every time the loop ends
				if(i==0) { // if i equals 0 we want to check the distance between the pacman and the fist fruit to be
					double dist = this.coords.distance3d(pman.getPoint(), copyFList.get(j).getPoint());
					if(j==0) { //first iteration we save the first element distance and index.
						shortestDist=dist;
						rememberIndex=j;
					}
					else { // check if there is a closer element then the save one.
						if(dist<shortestDist) {
							shortestDist=dist;
							rememberIndex=j;
						}
					}
				}
				else { // if i is greater then 0 we check the closest element to the last element we added to out new list.
					double dist = this.coords.distance3d(sortedFL.get(i-1).getPoint(), copyFList.get(j).getPoint());
					if(j==0) { //first iteration we save the first element distance and index.
						shortestDist=dist;
						rememberIndex=j;
					}
					else {// check if there is a closer element then the save one.
						if(dist<shortestDist) {
							shortestDist=dist;
							rememberIndex=j;
						}
					}
				}
				sortedFL.add(copyFList.get(rememberIndex)); //add the closest element to our new list
				copyFList.remove(rememberIndex); // delete the element the just added from the copy list to avoid duplicates.
			}
		}

		return sortedFL; //return the sorted list
	}

}
