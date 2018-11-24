package com.alan.common.vo;

import com.alan.common.constant.MessageTransferType;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author alan
 * @Date 2018/11/24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageInfo implements Serializable {
	private static final long serialVersionUID = -1554905843392596552L;

	private String              message;
	private Date createAt;

	private MessageTransferType messageTransferType;

	private Long                fromUid;
	private Long                toUid;
}
