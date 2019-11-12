package yangchuanosaurus.child.math;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class MathController {

    @RequestMapping(method = GET, path = "/math")
    public Math math(@RequestParam(value = "name", defaultValue = "count") String name) {
        return new Math(name);
    }

}
