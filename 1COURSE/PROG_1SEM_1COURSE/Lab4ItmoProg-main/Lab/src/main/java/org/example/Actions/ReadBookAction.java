package org.example.Actions;


import org.example.Exceptions.ItemNotFoundException;
import org.example.Human.Human;
import org.example.Things.Book;

public class ReadBookAction extends AbstractAction {
    public ReadBookAction() {
        super("ReadBook", "Action to read books");

    }

    public void execute(Human human) {
        Book book;
        try {
            book = (Book) human.getItemFromInventory(Book.class);
        } catch (ItemNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(human.getName() + " have read book " + book.getName());
    }
}
