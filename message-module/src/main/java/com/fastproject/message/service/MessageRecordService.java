package com.fastproject.message.service;

import com.fastproject.message.vo.record.MessageRecordCreate;
import com.fastproject.message.vo.record.MessageRecordUpdate;
import com.fastproject.message.vo.record.MessageRecordQuery;
import com.fastproject.message.vo.record.MessageRecordVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface MessageRecordService {

    Long save(MessageRecordCreate create);

    void update(MessageRecordUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    MessageRecordVo findById(Long id);

    PageVo<List<MessageRecordVo>> findPage(MessageRecordQuery query);
}
