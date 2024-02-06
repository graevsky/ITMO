package org.lab6.server.commsAndExecution.comms.assistants;

/**
 * This file needed to add elements to the certain position in array
 */
public class insElem {
    /**
     * @param array is the array, which will be modified
     * @param index is the position of element that will be added
     * @param element is the element that will be added
     * @return returns modified array
     */
    public static String[] insertElement(String[] array, int index, String element) {
        String[] newArray = new String[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, index);
        newArray[index] = element;
        System.arraycopy(array, index, newArray, index + 1, array.length - index);
        return newArray;
    }
}
