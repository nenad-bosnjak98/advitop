package com.njt.advitop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.FetchType.LAZY;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Vote {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long voteID;
    private VoteType voteType;
    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "postID", referencedColumnName = "postID")
    private Post post;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private User user;
}
