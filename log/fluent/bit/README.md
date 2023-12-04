
# Source build and Install 
[[DOCS] Fluent Bit - Build and Install](https://docs.fluentbit.io/manual/installation/sources/build-and-install)

## Requirements
- CMake >= 3.12
- Flex
- Bison >= 3
- YAML headers
- OpenSSL headers

## Build and Install

[CentOS Reference](https://github.com/fluent/fluent-bit/blob/master/dockerfiles/Dockerfile.centos7)

[Debian Reference](https://github.com/fluent/fluent-bit/blob/master/dockerfiles/Dockerfile)

```bash
sudo yum install -y \
  git gcc make cmake flex bison g++ \
  libyaml-devel \ # debian: libyaml-dev
  openssl-devel   # debian: libssl-dev
  
curl ftp://195.220.108.108/linux/centos/6.8/os/x86_64/Packages/libyaml-devel-0.1.3-4.el6_6.x86_64.rpm --output libyaml-devel-0.1.3-4.el6_6.x86_64.rpm

  
# Develop version
git clone https://github.com/fluent/fluent-bit
# Check current latest version on https://github.com/fluent/fluent-bit/releases
wget https://github.com/fluent/fluent-bit/archive/refs/tags/v2.2.0.tar.gz

tar xzvf v2.2.0.tar.gz
cd fluent-bit-2.2.0/build/

cmake ../

make

make install
```
