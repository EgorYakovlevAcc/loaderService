package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Porter {
    private Integer id;
    private String userName;
    Integer telegramId;
    String porterName;
}
