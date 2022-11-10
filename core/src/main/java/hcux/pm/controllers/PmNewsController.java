package hcux.pm.controllers;

import com.hand.hap.system.dto.DTOStatus;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import hcux.pm.dto.PmNews;
import hcux.pm.service.IPmNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

    @Controller
    public class PmNewsController extends BaseController{

    @Autowired
    private IPmNewsService service;


    @RequestMapping(value = {"/hcux/pm/news/query","/api/hcux/pm/news/query"})
    @ResponseBody
    public ResponseData query(PmNews dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectLists(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/hcux/pm/news/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<PmNews> dto, BindingResult result, HttpServletRequest request){
        IRequest requestCtx = createRequestContext(request);
        if(dto.get(0).getNewsId()!=null){
            dto.get(0).set__status(DTOStatus.UPDATE);
            dto.get(0).setLastUpdateDate(new Date());
          service.updateByPrimaryKey(requestCtx,dto.get(0));
          return new ResponseData(dto);
        }else{
            service.insertSelective(requestCtx,dto.get(0));
            return new ResponseData(dto);
        }
    }

    @RequestMapping(value = "/hcux/pm/news/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<PmNews> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
        @RequestMapping(value = {"/hcux/pm/news/detail/query","/api/hcux/pm/news/detail/query"})
        @ResponseBody
        public ResponseData newsQuery(PmNews dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
            IRequest requestContext = createRequestContext(request);
            return new ResponseData(new ArrayList<PmNews>(){{
                add(service.selectNews(requestContext,dto));
            }});
        }
    }