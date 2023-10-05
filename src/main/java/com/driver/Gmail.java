package com.driver;

import java.util.*;

public class Gmail extends Email {

    //maximum number of mails inbox can store
    int inboxCapacity;

    private List<Mail> inbox;
    private List<Mail> trash;

    private HashMap<String , Mail> allMails;


    //Inbox: Stores mails. Each mail has date (Date), sender (String), message (String). It is guaranteed that message is distinct for all mails.
    //Trash: Stores mails. Each mail has date (Date), sender (String), message (String)
    public Gmail(String emailId, int inboxCapacity) {
        super(emailId);
        this.inboxCapacity = inboxCapacity;
        inbox = new ArrayList<>();
        trash = new ArrayList<>();
        allMails = new HashMap<>();
    }

    public void receiveMail(Date date, String sender, String message){
        // If the inbox is full, move the oldest mail in the inbox to trash and add the new mail to inbox.
        // It is guaranteed that:
        // 1. Each mail in the inbox is distinct.
        // 2. The mails are received in non-decreasing order. This means that the date of a new mail is greater than equal to the dates of mails received already.
        if(inbox.size() == inboxCapacity){
            Mail oldMail = inbox.get(0);
            inbox.remove(0);
            trash.add(oldMail);
        }
        Mail newMail = new Mail(date , sender , message);
        inbox.add(newMail);
        allMails.put(message , newMail);
    }

    public void deleteMail(String message){
        // Each message is distinct
        // If the given message is found in any mail in the inbox, move the mail to trash, else do nothing
        Mail deleteMail = null;
        if(allMails.get(message) != null){
            deleteMail = allMails.get(message);
        } else return;

        //compare mails while iterating
        for(int i=0;i<inbox.size();i++){
            Mail currMail = inbox.get(i);
            if(currMail.message == deleteMail.message){
                trash.add(currMail);
                inbox.remove(i);
            }
        }
    }

    public String findLatestMessage(){
        // If the inbox is empty, return null
        // Else, return the message of the latest mail present in the inbox
        if(inbox.isEmpty()) return null;
        return inbox.get(inbox.size()-1).message;

    }

    public String findOldestMessage(){
        // If the inbox is empty, return null
        // Else, return the message of the oldest mail present in the inbox
        if(inbox.isEmpty()) return null;
        return inbox.get(0).message;

    }

    public int findMailsBetweenDates(Date start, Date end){
        //find number of mails in the inbox which are received between given dates
        //It is guaranteed that start date <= end date
        int count=0;
        for(int i=0;i<inbox.size();i++){
            Date currMailDate = inbox.get(i).date;
            if(currMailDate.compareTo(start) >= 0 && currMailDate.compareTo(end) <= 0) count++;
        }
        return count;

    }

    public int getInboxSize(){
        // Return number of mails in inbox
        return inbox.size();

    }

    public int getTrashSize(){
        // Return number of mails in Trash
        return trash.size();

    }

    public void emptyTrash(){
        // clear all mails in the trash
        trash.clear();

    }

    public int getInboxCapacity() {
        // Return the maximum number of mails that can be stored in the inbox
        return inboxCapacity;
    }
}
