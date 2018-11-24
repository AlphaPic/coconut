package com.alan.common.util;


import com.alan.common.constant.MessageTransferType;
import com.alan.common.vo.MessageInfo;

import java.util.Date;

/**
 * @author alan
 */
public class MessageUtil {
	private static final String HeartBeatMsg = "knock";

	public static MessageInfo generateHeartBeatMessage(){
		MessageInfo.MessageInfoBuilder builder = MessageInfo.builder();
		Date date = new Date(System.currentTimeMillis());
		return builder.messageTransferType(MessageTransferType.SINGLE_USER)
					  .createAt(date)
					  .message(HeartBeatMsg)
					  .fromUid(null)
			          .toUid(null)
					  .build();
	}
}
