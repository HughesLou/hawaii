package com.hughes.code.lintcode;

/**
 * Created by Hughes on 2016/8/14.
 */
public class SearchMatrix {
    /**
     * @param matrix: A list of lists of integers
     * @param: A number you want to search in the matrix
     * @return: An integer indicate the occurrence of target in the given matrix
     */
    public int searchMatrix(int[][] matrix, int target) {
        if (0 == matrix.length) {
            return 0;
        }
        return search(matrix, matrix.length, matrix[0].length, target);
    }

    private int search(int[][] matrix, int m, int n, int target) {
        int count = 0;
        int i = 0;
        int j = n - 1;
        while (i < m && j >= 0) {
            if (matrix[i][j] == target) {
                count++;
                i++;
                j--;
            } else if (matrix[i][j] < target) {
                i++;
            } else {
                j--;
            }
        }
        return count;
    }

    /**
     * @param matrix, a list of lists of integers
     * @param target, an integer
     * @return a boolean, indicate whether matrix contains target
     */
    public boolean searchMatrix2(int[][] matrix, int target) {
        if (0 == matrix.length) {
            return false;
        }
        if (matrix[0][0] > target) {
            return false;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        if (matrix[row -1][col -1] < target) {
            return false;
        }
        for (int i = row -1; i >= 0; --i) {
            if (matrix[i][0] == target) {
                return true;
            } else if (matrix[i][0] < target) {
                if (matrix[i][col -1] == target) {
                    return true;
                } else if (matrix[i][col -1] > target) {
                    for (int j = 1; j < col -1; ++j) {
                        if (matrix[i][j] == target) {
                            return true;
                        }
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] matrix = {{1,5,10,11,16,23,24,26,29,34,41,48,49,56,63,67,71,74,75},
                {97,118,131,150,160,182,202,226,251,273,289,310,326,349,368,390,401,412,428},
                {445,455,466,483,501,519,538,560,581,606,631,643,653,678,702,726,748,766,781},
                {792,817,837,858,872,884,901,920,936,957,972,982,1001,1024,1044,1063,1086,1098,1111},
                {1129,1151,1172,1194,1213,1224,1234,1250,1267,1279,1289,1310,1327,1348,1371,1393,1414,1436,1452},
                {1467,1477,1494,1510,1526,1550,1568,1585,1599,1615,1625,1649,1663,1674,1693,1710,1735,1750,1769}};
        boolean result = new SearchMatrix().searchMatrix2(matrix, 1086);
        System.out.println(result);
    }
}
