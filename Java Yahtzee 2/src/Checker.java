public class Checker {
    public Checker() {

    }
    int check(int type, int[] dice) {
        int index;
        int total = 0;
        for (int i = 0; i < dice.length; i++) {
            total += dice[i];
        }
        /*
            1: ones
            2: twos
            3: threes
            4: fours
            5: fives
            6: sixes
            7: three of a kind
            8: four of a kind
            9: full house
            10: small straight
            11: large straight
            12: Yahtzee
            13: chance
         */
        switch(type) {
            case 1:
                return arraySearch(1, dice);
            case 2:
                return arraySearch(2, dice) * 2;
            case 3:
                return arraySearch(3, dice) * 3;
            case 4:
                return arraySearch(4, dice) * 4;
            case 5:
                return arraySearch(5, dice) * 5;
            case 6:
                return arraySearch(6, dice) * 6;
            case 7:
                for (int i = 1; i < 7; i++) {
                    if (arraySearch(i, dice) >= 3) {
                        return total;
                    }
                }
                return 0;
            case 8:
                for (int i = 1; i < 7; i++) {
                    if (arraySearch(i, dice) >= 4) {
                        return total;
                    }
                }
                return 0;
            case 9:
                int first = 0;
                for (int i = 1; i < 7; i++) {
                    if (arraySearch(i, dice) == 3) {
                        first = i;
                    }
                }
                if (first == 0){
                    return 0;
                }
                int second = 0;
                for (int i = 1; i < 7; i++) {
                    if ((arraySearch(i, dice) == 2) && i != first) {
                        second = i;
                    }
                }
                if (second == 0) {
                    return 0;
                }
                return 25;
            case 10:
                int[] smallNums = {0, 0, 0, 0};
                index = 0;
                for (int i = 1; i < 7; i++) {
                    if (arraySearch(i, dice) != 0) {
                        smallNums[index] = i;
                        index++;
                        if (index >= 4) {
                            return 30;
                        }
                    }
                    else {
                        index = 0;
                    }
                }
                return 0;
            case 11:
                int[] largeNums = {0, 0, 0, 0, 0};
                index = 0;
                for (int i = 1; i < 7; i++) {
                    if (arraySearch(i, dice) != 0) {
                        largeNums[index] = i;
                        index++;
                        if (index >= 5) {
                            return 40;
                        }
                    }
                    else {
                        index = 0;
                    }
                }
                return 0;
            case 12:
                for (int i = 1; i < 7; i++) {
                    if (arraySearch(i, dice) >= 5) {
                        return 50;
                    }
                }
                return 0;
            case 13:
                return total;
        }
        return 0;
    }
    int arraySearch(int num, int[] dice) {
        int value = 0;
        for (int i = 0; i < dice.length; i++) {
            if (dice[i] == num) {
                value++;
            }
        }
        return value;
    }
    int most(int[] dice) {
        //work on this- sort dice values into most
        //2, 3, 4, 2, 2
        int highVal = 0;
        int highest = 0;
        int current = 0;
        for (int j = 1; j < 7; j++) {
            if (arraySearch(j, dice) >= highest) {
                highest = arraySearch(j, dice);
                highVal = j;
            }
        }
        return highVal;
    }
}
