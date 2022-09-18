package com.njt.advitop.transferobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    private Long postID;
    private String categoryName;
    private String postName;
    private String url;
    private String description;
}
