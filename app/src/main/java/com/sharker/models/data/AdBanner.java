package com.sharker.models.data;

import com.sharker.models.Banner;
import com.sharker.network.SharkerResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * 1. 类的用途
 * 2. @author：liqingyi
 * 3. @date：2017/4/8 16:52
 */
@HttpResponse(parser = SharkerResponseParser.class)
public class AdBanner extends BaseData {
    public List<Banner> banner;
}
