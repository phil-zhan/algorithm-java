package org.study.system.class48;

public class Code34_06_Convert {

    public static void main(String[] args) {
        System.out.println(new Code34_06_Convert().convert("PAYPALISHIRING", 3));
    }

    public String convert(String s, int numRows) {
        if (numRows == 0){
            return  "";
        }
        if (numRows == 1){
            return s;
        }

        char[] str = s.toCharArray();

        int colNums = (numRows - 1) * ((s.length() + ((2 * numRows - 2))) / (2 * numRows - 2));
        Character[][] matrix = new Character[numRows][colNums];
        process(matrix,str,0,numRows);

        StringBuilder builder = new StringBuilder();
        for (Character[] characters : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(characters[j] == null ? "  " : characters[j] + " ");
                builder.append(characters[j] == null ? "" : characters[j] + "");
            }
            System.out.println();
        }
        return builder.toString();
    }

    private void process(Character[][] matrix,char[] str,int index,int numRows){
        for (int col = 0; col < matrix[0].length; col+=((numRows - 1))) {
            int i = 0;
            int j = col;
            while (i < matrix.length && index < str.length){
                matrix[i++][j] = str[index++];
            }

            i--;
            while (i > 1 && index < str.length){
                matrix[--i][++j] = str[index++];
            }
        }
    }
}
