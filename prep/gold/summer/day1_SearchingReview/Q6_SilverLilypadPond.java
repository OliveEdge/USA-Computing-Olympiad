package day1_SearchingReview;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Q6_SilverLilypadPond {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        int height = Integer.parseInt(st.nextToken());
        int width = Integer.parseInt(st.nextToken());
        Coord currPos = new Coord(-1, -1, 0, 0);
        int endX = -1;
        int endY = -1;

        int[][] map = new int[width][height];
        for (int i = 0; i < height; i++) {
            st = new StringTokenizer(in.readLine());;
            for (int j = 0; j < width; j++) {
                int k = Integer.parseInt(st.nextToken());
                map[j][i] = k;
                if (k == 3) {
                    currPos.x = j;
                    currPos.y = i;
                } else if (k == 4) {
                    endX = j;
                    endY = i;
                }
            }
        }

        int[] dx = {2, 2, 1, -1, -2, -2, 1, -1};
        int[] dy = {1, -1, 2, 2, 1, -1, -2, -2};

        int[][][] visited = new int[width][height][2]; //placed, jumps
        long[][] ways = new long[width][height];
        ways[currPos.x][currPos.y] = 1;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                visited[i][j][0] = Integer.MAX_VALUE;
                visited[i][j][1] = Integer.MAX_VALUE;
            }
        }
        Queue<Coord> positions = new LinkedList<>();
        positions.add(currPos);
        while (!positions.isEmpty()) {
            currPos = positions.remove();
            for (int i = 0; i < 8; i++) {
                Coord newPos = new Coord(currPos.x + dx[i], currPos.y + dy[i], currPos.placed, currPos.jumps + 1);
                if (newPos.x < 0 || newPos.x >= width || newPos.y < 0 || newPos.y >= height) continue;
                if (map[newPos.x][newPos.y] == 2) continue;
                if (map[newPos.x][newPos.y] == 0) newPos.placed++;
                if (visited[newPos.x][newPos.y][0] == newPos.placed && visited[newPos.x][newPos.y][1] == newPos.jumps) {
                    ways[newPos.x][newPos.y] += ways[currPos.x][currPos.y];
                    continue;
                }
                if (visited[newPos.x][newPos.y][0] > newPos.placed) {
                    visited[newPos.x][newPos.y][0] = newPos.placed;
                    visited[newPos.x][newPos.y][1] = newPos.jumps;
                    ways[newPos.x][newPos.y] = 1;
                } else {
                    continue;
                }
                ways[newPos.x][newPos.y] = ways[currPos.x][currPos.y];
                if (newPos.x == endX && newPos.y == endY) continue;
                positions.add(newPos);
            }
        }
        if (ways[endX][endY] != 0) {
            System.out.println(visited[endX][endY][0]);
            System.out.println(visited[endX][endY][1]);
            System.out.println(ways[endX][endY]);
        } else {
            System.out.println("-1");
        }
    }
    static class Coord {
        int x;
        int y;
        int placed;
        int jumps;
        Coord (int x, int y, int placed, int jumps) {
            this.x = x;
            this.y = y;
            this.placed = placed;
            this.jumps = jumps;
        }
    }
}
