package org.seckill.web;

import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * GET /seckill/
 * GET /seckill/{id}/detail
 * GET /seckill/time/now
 * POST /seckill/{id}/exposer
 * POST /seckill/{id}/{md5}/execution
 */
@Controller
@RequestMapping("/seckill")   //url:/Ä£¿é/
public class SeckillController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SeckillService seckillService;
	
	//ajax json
	@RequestMapping(value="/{seckillId}/exposer",method=RequestMethod.POST)
	public SeckillResult<Exposer> exposer(Long seckillId){
		SeckillResult<Exposer> result;
		try {
			Exposer exposer = seckillService.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exposer>(true,exposer);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			result = new SeckillResult<Exposer>(false,e.getMessage());
		}
		
		
		return null;					
	}
	
	@RequestMapping(value="/list",method = RequestMethod.GET)
    public String list(Model model){
		List<Seckill> list = seckillService.getSeckillList();
		model.addAttribute("list",list);
		return "list";
    }
	
	@RequestMapping(value="{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId,Model model){
		if(seckillId == null){
			return "redirect:/seckill/list";
		}
		Seckill seckill = seckillService.getById(seckillId);
		if(seckill == null){
			return "forward:/seckill/list";
		}
		model.addAttribute("seckill", seckill);
		return "detail";
	}
}
