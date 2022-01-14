package it.kekw.clowngg.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("match")
public class ClownMatchMgrController implements ClownMatchMgr {

    // @Autowired
    // private ClownMatchMgrImpl clownMatchMgrImpl;

    @Override
    @GetMapping("/ping")
    public String ping() {
        // return clownMatchMgrImpl.ping();
        return "true";
    }
    
}
