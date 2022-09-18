package com.njt.advitop.transferobject;

import com.njt.advitop.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteObject {
    private VoteType voteType;
    private Long postID;
}
