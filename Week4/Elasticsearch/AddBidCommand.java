package com.example.sharingapp;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Command to add a bid
 */
public class AddBidCommand extends Command {

    private BidList bid_list;
    private Bid bid;
    //private Context context;

    public AddBidCommand(Bid bid) {
       // this.bid_list = bid_list;
        this.bid = bid;
       // this.context = context;
    }

    /**
     * Saving the bids remotely on the server, instead of locally on the host machine.
     * */
    public void execute() {
        ElasticSearchManager.AddBidTask add_bid_task = new ElasticSearchManager.AddBidTask();
        add_bid_task.execute(bid);

        try {
            if (add_bid_task.get()) {       // successfully saves on remote server
                bid_list.addBid(bid);       // add it to the bid list
                super.setIsExecuted(true);  // done successfully
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            super.setIsExecuted(false);
        }
    }

    /*
    // Save bid locally
    public void execute(){
        bid_list.addBid(bid);
        super.setIsExecuted(bid_list.saveBids(context));
    } */
}
