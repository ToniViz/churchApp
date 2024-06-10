package com.app.church.church.entities.users;

import java.sql.Blob;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @NotBlank
    private String text;

    private Blob media;

    private Date dateSend;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column(columnDefinition = "boolean default false")
    private boolean senderDeleted;
    @Column(columnDefinition = "boolean default false")
    private boolean receiverDeleted;


    public Message() {
        this.dateSend = new Date();
    }

    public Message(String title, String text) {
        this();
        this.title = title;
        this.text = text;
    }

    public Message(String title, String text, Blob media) {
        this.title = title;
        this.text = text;
        this.media = media;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Blob getMedia() {
        return media;
    }

    public void setMedia(Blob media) {
        this.media = media;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    
    
    

    public boolean isSenderDeleted() {
        return senderDeleted;
    }

    public void setSenderDeleted(boolean senderDeleted) {
        this.senderDeleted = senderDeleted;
    }

    public boolean isReceiverDeleted() {
        return receiverDeleted;
    }

    public void setReceiverDeleted(boolean receiverDeleted) {
        this.receiverDeleted = receiverDeleted;
    }

    

    @Override
    public String toString() {
        return "{id=" + id + ", title=" + title + ", text=" + text + ", media=" + media + ", dateSend="
                + dateSend + ", sender=" + sender + ", receiver=" + receiver + ", senderDeleted=" + senderDeleted
                + ", receiverDeleted=" + receiverDeleted + "}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Message other = (Message) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    public Date getDateSend() {
        return dateSend;
    }

    public void setDateSend(Date dateSend) {
        this.dateSend = dateSend;
    }


    

    

    

}
