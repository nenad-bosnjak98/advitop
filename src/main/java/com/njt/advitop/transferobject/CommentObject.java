package com.njt.advitop.transferobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentObject {
    private Long ID;
    private Long postID;
    private Instant createdDate;
    private String text;
    private String username;
}
