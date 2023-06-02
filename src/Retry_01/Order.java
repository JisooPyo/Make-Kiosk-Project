package Retry_01;

import java.util.*;

public class Order {
    ProductDB productDB = new ProductDB();
    static List< String[] > orderList = new ArrayList<>();

    Map< String, String > totalProductList = new HashMap<>();

    public void showOrder() {
        System.out.println( "아래와 같이 주문 하시겠습니까?\n" );
        System.out.println( "[ Orders ]" );
        makeList( orderList );
        System.out.println();
        System.out.println( "[ Total ]" );
        System.out.println( "W " + getSum( orderList ) + "\n" );
        System.out.println( "1. 주문        2. 메뉴판" );
    }

    public void addOrder( String[] arr ) {
        orderList.add( arr );
        System.out.println( "\n" + arr[0] + " 가 장바구니에 추가되었습니다.\n" );
    }

    public void makeList( List< String[] > list ) {
        List< String[] > list2 = countList( list );
        for ( int i = 0 ; i < list2.size() ; i++ ) {
            String name = list2.get( i )[0];
            String price = list2.get( i )[1];
            String desc = list2.get( i )[2];
            String num = list2.get( i )[3];
            String space = " ".repeat( menuPartLength( list2 ) - name.length() );
            System.out.println( name + space + "| W " + price + " | " + num + "개 | " + desc );
        }
    }

    public int menuPartLength( List< String[] > list ) {
        int menuPartLength = 0;
        for ( int i = 0 ; i < list.size() ; i++ ) {
            menuPartLength = Math.max( list.get( i )[0].length(), menuPartLength );
        }
        menuPartLength += 2;
        return menuPartLength;
    }

    public double getSum( List< String[] > list ) {
        double sum = 0;
        for ( String[] strArr : list ) {
            sum += Double.parseDouble( strArr[1] );
        }
        return Math.round( sum * 10 ) / 10.0;
    }

    public String[] pickProduct( int menu, int detailMenu ) {
        Scanner sc = new Scanner( System.in );

        Menu[] menuArr = productDB.menuArr();
        Product product = productDB.menuMap().get( menuArr[menu - 1].name )[detailMenu - 1];
        String[] arr;

        if ( !( product.op1.equals( "oneSize" ) ) ) {
            String op1 = product.op1;
            String op1_price = Double.toString( product.op1_price );
            String op2 = product.op2;
            String op2_price = Double.toString( product.op2_price );

            System.out.println( "위 메뉴의 어떤 옵션으로 추가하시겠습니까?" );
            System.out.println( "1. " + op1 + "(W " + op1_price + ")        2. " + op2 + "(W " + op2_price + ")" );

            int pickOp = sc.nextInt();
            if ( pickOp == 1 ) {
                arr = new String[] { product.name + "(" + op1 + ")", op1_price, product.desc, "1" };
            } else {
                arr = new String[] { product.name + "(" + op2 + ")", op2_price, product.desc, "1" };
            }
        } else {
            arr = new String[] { product.name, Double.toString( product.op1_price ), product.desc, "1" };
        }
        System.out.println( arr[0] + "  | W " + arr[1] + " | " + arr[2] );
        System.out.println( "위 메뉴를 장바구니에 추가하시겠습니까?" );
        System.out.println( "1. 확인        2. 취소" );

        return arr;
    }

    public List< String[] > countList( List< String[] > list ) {
        List< String[] > list2 = deleteOverlap( list );
        for ( int i = 0 ; i < list2.size() ; i++ ) {
            int count = 0;
            for ( int j = 0 ; j < list.size() ; j++ ) {
                if ( Arrays.equals( list2.get( i ), list.get( j ) ) ) {
                    count++;
                }
            }
            list2.get( i )[3] = Integer.toString( count );
        }
        return list2;
    }

    public boolean containStrArr( List< String[] > list, String[] strArr ) {
        boolean containStrArr = false;
        for ( int i = 0 ; i < list.size() ; i++ ) {
            if ( Arrays.equals( list.get( i ), strArr ) ) {
                containStrArr = true;
                break;
            }
        }
        return containStrArr;
    }

    public List< String[] > deleteOverlap( List< String[] > list ) {
        List< String[] > list2 = new ArrayList<>();
        for ( int i = 0 ; i < list.size() ; i++ ) {
            String[] strArr = list.get( i );
            if ( !containStrArr( list2, strArr ) ) {
                list2.add( strArr );
            }
        }
        return list2;
    }

}
