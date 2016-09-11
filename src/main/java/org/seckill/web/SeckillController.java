package org.seckill.web;

import java.util.Date;
import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillClosedException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**url:/模块/资源/{id}/细分 /seckill/list
 * GET /seckill/ 
 * GET /seckill/{id}/detail 
 * GET /seckill/time/now 
 * POST	/seckill/{id}/exposer  
 * POST /seckill/{id}/{md5}/execution
 */
@Controller
@RequestMapping("/seckill")
// url:/ģ��/
public class SeckillController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SeckillService seckillService;
	
    @RequestMapping(value="/time/now",method = RequestMethod.GET)
    @ResponseBody
	public SeckillResult<Long> time(){
		Date now = new Date();
		return new SeckillResult(true,now.getTime());
	}
	
	@RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(
								@PathVariable("seckillId") long seckillId,
								@PathVariable("md5") String md5,
								@CookieValue(value = "killPhone", required = false) Long userphone
			) {
		// springmvc valid
		if (userphone == null) {
			return new SeckillResult<SeckillExecution>(false, "未注册");
		}
		// SeckillResult<SeckillExecution> result;
		try {
			SeckillExecution execution = seckillService.executeSeckillProcedure(
					seckillId, userphone, md5);
			/*SeckillExecution execution = seckillService.executeSeckill(
					seckillId, userphone, md5);*/
			return new SeckillResult<SeckillExecution>(true, execution);
		} catch (RepeatKillException e) {
			SeckillExecution execution = new SeckillExecution(seckillId,
					SeckillStatEnum.REPEAT_KILL);
			return new SeckillResult(true, execution);
		} catch (SeckillClosedException e) {
			SeckillExecution execution = new SeckillExecution(seckillId,
					SeckillStatEnum.END);
			return new SeckillResult(true, execution);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			// TODO
			SeckillExecution execution = new SeckillExecution(seckillId,
					SeckillStatEnum.INNER_ERROR);
			return new SeckillResult(true, execution);
		}

		// return null;
	}

	// ajax json
	@RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public SeckillResult<Exposer> exposer(@PathVariable("seckillId")Long seckillId) {
		SeckillResult<Exposer> result;
		try {
			Exposer exposer = seckillService.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exposer>(true, exposer);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result = new SeckillResult<Exposer>(false, e.getMessage());
		}

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		List<Seckill> list = seckillService.getSeckillList();
		model.addAttribute("list", list);
		return "list";
	}

	@RequestMapping(value = "{seckillId}/detail", method = RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
		if (seckillId == null) {
			return "redirect:/seckill/list";
		}
		Seckill seckill = seckillService.getById(seckillId);
		if (seckill == null) {
			return "forward:/seckill/list";
		}
		model.addAttribute("seckill", seckill);
		return "detail";
	}
}
