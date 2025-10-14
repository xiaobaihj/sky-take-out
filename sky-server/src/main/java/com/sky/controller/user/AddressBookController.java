package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user/addressBook")
@RestController
@Api(tags = "C端地址簿接口")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;
    /**
     * 查询当前登陆用户所有的地址信息
     * @return
     */
    @ApiOperation("查询当前登陆用户所有的地址信息")
    @GetMapping("/list")
    public Result list(){
        AddressBook addressBook = new AddressBook();
        Long userId = BaseContext.getCurrentId();
        addressBook.setId(userId);
        List<AddressBook> list = addressBookService.list(addressBook);
        return Result.success(list);
    }

    /**
     * 新增地址
     * @param addressBook
     * @return
     */
    @ApiOperation("新增地址")
    @PostMapping
    public Result<AddressBook> save(@RequestBody AddressBook addressBook){
        addressBookService.save(addressBook);
        return Result.success();
    }

    /**
     * 根据id查询地址，用于地址回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<AddressBook> getById(@PathVariable Long id){
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }

    /**
     * 根据id修改地址
     * @param addressBook
     * @return
     */
    @PutMapping
    @ApiOperation("根据id修改地址")
    public Result update(@RequestBody AddressBook addressBook){
        addressBookService.update(addressBook);
        return Result.success();
    }

    /**
     * 设置默认地址
     * @return
     */
    @PutMapping("/default")
    @ApiOperation("设置默认地址")
    public Result setDefault(@RequestBody AddressBook addressBook){
        addressBookService.setDefault(addressBook);
        return Result.success();
    }

    /**
     * 根据id删除地址
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("根据id删除地址")
    public Result deleteById(Long id){
        addressBookService.deleteById(id);
        return Result.success();
    }

    /**
     * 查询默认地址
     * @return
     */
    @GetMapping("/default")
    @ApiOperation("查询默认地址")
    public Result<AddressBook> getDefault(){
        AddressBook addressBook = new AddressBook();
        Long userId = BaseContext.getCurrentId();
        addressBook.setUserId(userId);
        addressBook.setIsDefault(1);

        List<AddressBook> list = addressBookService.list(addressBook);

        if (list != null && list.size()==1){
            return Result.success(list.get(0));
        }
        return Result.error("没有查询到默认地址");
    }
}
