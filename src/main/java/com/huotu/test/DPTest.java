package com.huotu.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by helloztt on 2016/5/4.
 */
public class DPTest {

    private List<DPModel> getMinPath(List<Integer> orderGoodsNums, List<DPModel> shopGoods, List<DPModel> path) throws Exception {
        //此结点路径不满足条件
        if (shopGoods.size() == 0) {
            return null;
        }
        DPModel firstShopGoods = shopGoods.get(0).clone();
        boolean isOver = true;
        List<Integer> shopGoodsNum = firstShopGoods.getNums();
        //将此结点放入背包后，剩余资源
        List<Integer> nextGoodsNum = new ArrayList<>();
        //此结点需要的资源数量
        List<Integer> needGoodsNums = new ArrayList<>();
        for (int j = 0; j < orderGoodsNums.size(); j++) {
            needGoodsNums.add(Math.min(orderGoodsNums.get(j), shopGoodsNum.get(j)));
            nextGoodsNum.add((orderGoodsNums.get(j) - shopGoodsNum.get(j)) >= 0 ? (orderGoodsNums.get(j) - shopGoodsNum.get(j)) : 0);
            if (nextGoodsNum.get(j) > 0) {
                isOver = false;
            }
        }
        //背包资源用尽，返回路径
        if (isOver) {
            List<DPModel> overPath = new ArrayList<>();
            overPath.addAll(path);
            firstShopGoods.setProvoidNums(needGoodsNums);
            overPath.add(firstShopGoods);
            overPath.forEach(p -> {
                System.out.print(p.getName() + "->");
            });
            System.out.println();
            return overPath;
        } else {
            List<DPModel> nextShopGoods = new ArrayList<>();
            nextShopGoods.addAll(shopGoods);
            nextShopGoods.remove(0);
            List<DPModel> nextPath = new ArrayList<>();
            nextPath.addAll(path);
            //将此结点放入背包后，获取路径
            firstShopGoods.setProvoidNums(needGoodsNums);
            nextPath.add(firstShopGoods);
            List<DPModel> putIntoBag = getMinPath(nextGoodsNum, nextShopGoods, nextPath);
            nextPath.clear();
            nextPath.addAll(path);
            List<DPModel> notPutIntoBag = getMinPath(orderGoodsNums, nextShopGoods, nextPath);
            int putIntoBagTotalValue = putIntoBag == null ? -1 : (putIntoBag.stream().mapToInt(DPModel::getValue).sum());
            int notPutIntoBagTotalValue = notPutIntoBag == null ? -1 : (notPutIntoBag.stream().mapToInt(DPModel::getValue).sum());
            if (notPutIntoBagTotalValue > 0 && notPutIntoBagTotalValue < putIntoBagTotalValue) {
                return notPutIntoBag;
            } else if (putIntoBagTotalValue > 0) {
                return putIntoBag;
            } else {
                return null;
            }
        }
    }

    public static void main(String args[]) throws Exception {
        List<Integer> orderGoodsNums = Arrays.asList(5, 4, 3);
        List<DPModel> shopGoods = new ArrayList<>();
        DPModel a = new DPModel("a", 1, Arrays.asList(5, 0, 0));
        DPModel b = new DPModel("b", 1, Arrays.asList(3, 2, 1));
        DPModel c = new DPModel("c", 1, Arrays.asList(2, 2, 2));
        DPModel d = new DPModel("d", 1, Arrays.asList(0, 4, 3));
        shopGoods.add(a);
        shopGoods.add(b);
        shopGoods.add(c);
        shopGoods.add(d);
        List<DPModel> path = new ArrayList<>();
        long start = System.currentTimeMillis();
        path = new DPTest().getMinPath(orderGoodsNums, shopGoods, path);
        long end = System.currentTimeMillis();
        System.out.println("it takes " + (end - start) + " ms");
        System.out.println("--------------------");
        if (path != null) {
            path.forEach(p -> {
                System.out.print(p.getName() + ":[");
                if (p.getProvoidNums() != null) {
                    p.getProvoidNums().forEach(num -> {
                        System.out.print(num + ",");
                    });
                }
                System.out.print("]->");
            });
        }
    }
}
