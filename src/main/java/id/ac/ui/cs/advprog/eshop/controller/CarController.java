package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/create")
    public String createCarPage(Model model) {
        model.addAttribute("car", new Car());
        return "createCar";
    }

    @PostMapping("/create")
    public String createCarPost(@ModelAttribute Car car) {
        carService.create(car);
        return "redirect:/car/list";
    }

    @GetMapping("/list")
    public String carListPage(Model model) {
        List<Car> allCars = carService.findAll();
        model.addAttribute("cars", allCars);
        return "carList";
    }

    @GetMapping("/edit/{id}")
    public String editCarPage(@PathVariable("id") String id, Model model) {
        Car car = carService.findById(id);
        if (car == null) {
            return "redirect:/car/list";
        }
        model.addAttribute("car", car);
        return "editCar";
    }

    @PostMapping("/edit/{id}")
    public String editCarPost(@ModelAttribute Car car, @PathVariable("id") String id) {
        car.setCarId(id);
        carService.update(id, car);
        return "redirect:/car/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable("id") String id) {
        carService.deleteCarById(id);
        return "redirect:/car/list";
    }
}
